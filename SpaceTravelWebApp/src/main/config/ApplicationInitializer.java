package main.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// We have to provide our application with a DispatcherServlet. To get it, we have to extend the follow interface:
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{

	@Override
	protected Class<?>[] getRootConfigClasses() 
	{
		return new Class[] {DatabaseConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() 
	{
		// Here we specify that the config for the dispatcher should be found in WebConfig class
		// added DatabaseConfig.class now that we're using a database
		return new Class[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() 
	{
		// Here we specify our application's entry point - the page which opens when someone starts the app
		return new String[] {"/"};
	}

}
