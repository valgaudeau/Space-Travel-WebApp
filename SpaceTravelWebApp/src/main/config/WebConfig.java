package main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan("main")
public class WebConfig implements WebMvcConfigurer // we implement the Mvc configuration interface
{
	// The view resolver, whose role is to return the correct view based on the name received by the controller
	
	@Autowired
	private WebApplicationContext context;
	
	@Bean
	public ServletContextTemplateResolver templateResolver() 
	{
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(context.getServletContext());
		// Now we need to give the prefix and suffix so the resolver knows the location of the return page
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".html");
		return resolver;
	}
	
	// Now we need the Spring template engine:
	@Bean
	public SpringTemplateEngine templateEngine()
	{
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		// we have to add the dialect for spring security 
		templateEngine.addDialect(new SpringSecurityDialect());
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}
	
	// The last thing we need is the ViewResolver for Thymeleaf:
	@Bean
	public ThymeleafViewResolver viewResolver()
	{
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) 
	{
		configurer.enable();
	}
	
}