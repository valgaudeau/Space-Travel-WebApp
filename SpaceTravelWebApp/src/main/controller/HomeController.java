package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController 
{
	// The following mapping tells Spring that this method has to be invoked when someone starts the application
	@RequestMapping("/")
	public String getHome() 
	{ 
		return "home"; 
	}
}
