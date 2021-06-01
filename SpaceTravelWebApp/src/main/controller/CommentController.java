package main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import main.model.Comment;
import main.model.SpaceTrip;
import main.service.CommentService;
import main.service.SpaceTripService;

@Controller
public class CommentController 
{
	@Autowired
	private SpaceTripService spaceTripService;
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/addComment")
	public String addCommentToTour(Model model)
	{
		List<SpaceTrip> spaceTrips = spaceTripService.getAll();
		model.addAttribute("spaceTrips", spaceTrips);
		model.addAttribute("comment", new Comment());
		return "form-comment";
	}
	
	@PostMapping("/processCommentData")
	public String processCommentData(@ModelAttribute Comment comment)
	{
		commentService.save(comment);
		return "home";
	}
}
