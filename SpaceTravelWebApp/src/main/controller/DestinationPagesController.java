package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DestinationPagesController 
{
	@RequestMapping("/jupiter-description")
	public String getJupiterDescription() { return "jupiter-description"; }
	
	@RequestMapping("/mars-description")
	public String getMarsDescription() { return "mars-description"; }
	
	@RequestMapping("/venus-description")
	public String getVenusDescription() { return "venus-description"; }
	
	@RequestMapping("/uranus-description")
	public String getUranusDescription() { return "uranus-description"; }
	
	@RequestMapping("/pluto-description")
	public String getPlutoDescription() { return "pluto-description"; }
	
	@RequestMapping("/mercury-description")
	public String getMercuryDescription() { return "mercury-description"; }
	
	@RequestMapping("/neptune-description")
	public String getNeptuneDescription() { return "neptune-description"; }
	
	@RequestMapping("/saturn-description")
	public String getSaturnDescription() { return "saturn-description"; }
	
	@RequestMapping("/about")
	public String getAboutPage() { return "about"; }
	
	@RequestMapping("/contact")
	public String getContactPage() { return "contact"; }
}
