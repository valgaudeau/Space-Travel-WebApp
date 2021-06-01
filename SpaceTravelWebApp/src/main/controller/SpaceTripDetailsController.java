package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import main.model.SpaceTrip;
import main.model.SpaceTripDetails;
import main.service.SpaceTripDetailsService;
import main.service.SpaceTripService;

@Controller
public class SpaceTripDetailsController 
{
	@Autowired
	private SpaceTripService spaceTripService;
	
	@Autowired
	private SpaceTripDetailsService spaceTripDetailsService;
	
	@GetMapping("/showSpaceTripDetails/{tripId}")
	public String showSpaceTripDetails(@PathVariable int tripId, Model model)
	{
		SpaceTrip spaceTrip = spaceTripService.getById(tripId);
		if(spaceTrip != null)
		{
			model.addAttribute("spaceTrip", spaceTrip);
			return "spacetrip-details";
		}
		return "redirect:/showTrips";
	}
	
	@GetMapping("/editSpaceTripDetails/{tripId}")
	public String editSpaceTripDetails(@PathVariable int tripId, Model model)
	{
		SpaceTrip spaceTrip = spaceTripService.getById(tripId);
		if(spaceTrip != null)
		{
			model.addAttribute("spaceTripDetails", spaceTrip.getSpaceTripDetails());
			return "form-spacetrip-details";
		}
		return "redirect:/showTrips";
	}
	
	@PostMapping("//processSpaceTripDetailsForm")
	public String processSpaceTripDetailsForm(@ModelAttribute SpaceTripDetails spaceTripDetails)
	{
		spaceTripDetailsService.saveOrUpdate(spaceTripDetails);
		return "redirect:/showTrips";
	}
}
