package main.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("main") // package name to be scanned
@PropertySource("/resources/database.properties") // this tells it where to go look for the properties file
@EnableTransactionManagement
@EnableJpaRepositories("main.repository")
public class DatabaseConfig 
{
	// Environment is an interface representing the environment in which the application is running. It can get profiles and properties of the app
	@Autowired
	private Environment environment;
	
	@Bean
	public DataSource getDataSource() // import the package for DataSource from SQL
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		// Now we use the environment variable to retrieve all of the information written in the database.properties file
		// In brackets are the names from the database.properties file attributes
		dataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
		dataSource.setUrl(environment.getProperty("jdbc.url"));
		dataSource.setUsername(environment.getProperty("jdbc.username"));
		dataSource.setPassword(environment.getProperty("jdbc.password"));
		
		return dataSource;
	}
	
	@Bean(name = "entityManagerFactory")
	public LocalSessionFactoryBean sessionFactoryBean() // Here we configure the session factory
	{
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(getDataSource());
		localSessionFactoryBean.setPackagesToScan(new String[] {"main"});
		// We need to set all the hibernate properties inside the sessionFactoryBean:
		localSessionFactoryBean.setHibernateProperties(hibernateProperties());
		return localSessionFactoryBean;
	}
	
	// We use this field to get all the hibernate properties that we need in the SessionFactoryBean
	private final Properties hibernateProperties()
	{
		Properties properties = new Properties();
		// In brackets are the names from the database.properties file attributes
		properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
		properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}
	
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager()
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactoryBean().getObject());
		return transactionManager;
	}
	
}

