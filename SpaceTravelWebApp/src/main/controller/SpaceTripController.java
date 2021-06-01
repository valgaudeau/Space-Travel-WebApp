package main.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import main.model.SpaceTrip;
import main.model.User;
import main.repository.UserRepository;
import main.service.SpaceTripService;
import main.service.UserService;

@Controller
public class SpaceTripController 
{
	@Autowired
	SpaceTripService spaceTripService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	// Method to add a new space trip
	@GetMapping("/addSpaceTrip")
	/*
	 * Simply put, the model can supply attributes used for rendering views.
	 * To provide a view with usable data, we simply add this data to its Model object
	 * see https://www.baeldung.com/spring-mvc-model-model-map-model-view 
	 */
	public String showForm(Model model) 
	{
		model.addAttribute("spaceTrip", new SpaceTrip());
		return "form";
	}
	
	// This method takes an object from the form page where we collect data about our trips, and saves it
	@PostMapping("/processFormData")
	public String SaveTripData(@Valid @ModelAttribute SpaceTrip spaceTrip, BindingResult bindingResult)
	{
		/*
		 *  @Valid annotation triggers validations for User, BindingResult holds the result of a validation and errors that may have occurred
		 *  If an error has occurred, then we return the form again
		 */
		
		if (bindingResult.hasErrors())
		{
			return "form";
		}
		spaceTripService.saveOrUpdate(spaceTrip);
		return "redirect:/showTrips";
	}
	
	// This method will present all existing Space Trips
	@GetMapping("/showTrips")
	public String showAllSpaceTrips(Model model)
	{
		List<SpaceTrip> spaceTrips = spaceTripService.getAll();
		model.addAttribute("spaceTrips", spaceTrips);
		return "spaceTrips";
	}
	
	@GetMapping("/editSpaceTrip/{id}") // Note how we include the {id} in the resource mapping so that the id of the entity shows up
	public String editSpaceTrip(@PathVariable int id, Model model)
	{
		SpaceTrip spaceTrip = spaceTripService.getById(id);
		if(spaceTrip != null)
		{
			model.addAttribute("spaceTrip", spaceTrip);
			return "form";
		}
		return "redirect:/showTrips";
	}
	
	@GetMapping("/deleteSpaceTrip/{id}")
	public String deleteSpaceTrip(@PathVariable int id)
	{
		SpaceTrip spaceTrip = spaceTripService.getById(id);
		if(spaceTrip != null)
		{
			spaceTripService.delete(id);
		}
		return "redirect:/showTrips";
	}
	
	@GetMapping("addUserToSpaceTrip/{spaceTripId}")
	public String addUserToSpaceStrip(@PathVariable int spaceTripId, Principal principal, Model model) 
	{
		// principal is the currently logged in user - see https://stackoverflow.com/questions/37499307/whats-the-principal-in-spring-security
		if(spaceTripService.userAlreadySignedUp(principal.getName(), spaceTripId) == false) // if the user with that id isn't already signed up to the spacetrip with that id, then we return the form
		{
			User user = new User();
			// Add user to our model, which will be passed to the Thymeleaf page. Note that the value between quotes is the identifier we use in thymeleaf, and the second argument is the actual object.
			model.addAttribute("user", user);
			// "A parameter passed through the model will be accessible from within the thymeleaf HTML code" - see https://www.baeldung.com/spring-mvc-model-model-map-model-view
			// THINK ABOUT HOW IT WORKS: First request goes out to front servlet, goes to controller, which returns a page etc. This helps reason how to write
			SpaceTrip selectedSpaceTrip = new SpaceTrip();
			selectedSpaceTrip.setId(spaceTripId);
			// We also need to add spaceTrip here so that we can have the spaceTrip.id on the Thymeleaf page, and pass that Id to processSignupData method.
			model.addAttribute("spaceTrip", selectedSpaceTrip);
			return "join-trip-signup-page";
		}
		// else, we show a message that user is already signed up
		return "join-trip-signup-page-error";
	}
	
	
	@PostMapping("/processSignupData/{spaceTripId}") // IMPORTANT NOTE ON BINDING RESULT: In the list of arguments, it must follow the modelattribute - see https://stackoverflow.com/questions/29878055/400-bad-request-when-validation-model-attribute-with-spring-mvc
	public String SaveSignupData(@PathVariable int spaceTripId, Principal principal,  @ModelAttribute SpaceTrip spaceTrip, @Valid @ModelAttribute User user, BindingResult bindingResult, Model model) // @Valid annotation triggers validations for User, BindingResult holds the result of a validation and errors that may have occurred
	{
		// PROBLEM: WHEN ERROR HAPPENS, THE SPACETRIPID URI VALUE GETS SET TO 0 INSTEAD OF WHATEVER THE ACTUAL VALUE SHOULD BE. SO THE METHOD GETS CALLED AGAIN BUT WITH THE WRONG SPACETRIP VALUE
		String loginOfCurrentUser = principal.getName();
		
		if (bindingResult.hasErrors()) // if an error is held by BindingResult, then we return the signup page again since we can't proceed
		{
			/*
			 *  PROBLEM SOLVED: When we go into this part of the code, we need to send the Thymeleaf page the spaceTrip object we're working with once again. Otherwise, the URI holds spaceTripId value of 0.
			 *  Therefore, when we tried to call the addUserToTrip method, it returned a no such element exception because it couldn't find the spaceTrip object we were looking for in the database.
			 *  We tested if principal.getName() actually worked, and it gave an error due to failure to launch lazy initialization. So we also had to use the solution where we saved the login of the 
			 *  current user prior to anything else happening on the page.
			 */
			SpaceTrip selectedSpaceTrip = new SpaceTrip();
			selectedSpaceTrip.setId(spaceTripId);
			model.addAttribute("spaceTrip", selectedSpaceTrip);
			return "join-trip-signup-page";
		}
			User user1 = userRepository.findByLogin(loginOfCurrentUser);
			user1.setEmail(user.getEmail());
			user1.setFirstName(user.getFirstName());
			user1.setLastName(user.getLastName());
			userService.saveOrUpdate(user1);
			
			/* 
			   Reason it didn't appear to save anything was that it retrieved currently logged in user, and merged it with itself WITHOUT the data collected from the page. So we just ended up with the same thing
			   in the database. To solve this, we got the values from user (which contains the data) and used them to set the values from currentlyLoggedInUser.
			   There must be a better way of doing this, like what I was originally trying to do - in model.addAttribute from addUserToSpaceTrip, directly add the currentlyLoggedInUser object such that
			   it contains the data we need directly without having to set it like this. But for now, it seems to work. Can try to improve this later.
			*/
			
			// Final step: We need a method which actually associates the user and the spacetrip.
			spaceTripService.addUserToTrip(spaceTripId, user1.getLogin()); 

			return "join-trip-signup-page-success";
	}
	 
}
