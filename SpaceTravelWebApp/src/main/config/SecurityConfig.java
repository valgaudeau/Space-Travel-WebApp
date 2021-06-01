package main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() // this will be used to encode the password
	{
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth
		.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select login, password, enabled from user where login=?")
		.authoritiesByUsernameQuery("select login, role from role where login=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception // We use this method to configure access to the different parts of our application
	{
		/*
		 *  This configuration specifies that the home page and login page are accessible to all users, no matter if they are logged in or
		 *  not, and we also specify the page to which the user will be redirected after login and logout operation.
		 */
		http.authorizeRequests()
		.antMatchers("/", "/login")
			.permitAll()
		.antMatchers("/addSpaceTrip", "/editSpaceTrip")
			.hasAnyRole("ADMIN", "EMPLOYEE") // Only users with admin or employee role can now access addTour
		.antMatchers("/deleteSpaceTrip")
			.hasAnyRole("ADMIN")
		.antMatchers("/addComment", "/addUserToSpaceTrip")
			.hasAnyRole("CLIENT")
		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/checkUserAccount")
			.defaultSuccessUrl("/") // here we specify the page to which the user will be redirected after successful login
			.permitAll()
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/") // here we specify the page to which the user will be redirected after successful logout
			.invalidateHttpSession(true)
			.permitAll();
	}
	
}
