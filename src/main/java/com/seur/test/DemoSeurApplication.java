package com.seur.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.seur.test.security.SystemUser;
import com.seur.test.security.SystemUserRepository;

/*@SpringBootApplication
public class DemoSeurApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSeurApplication.class, args);
	}
}*/

@SpringBootApplication
public class DemoSeurApplication extends SpringBootServletInitializer 
{
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	public DemoSeurApplication()
	{
		super();
	}
	
    public static void main(String[] args) 
    {

    	SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
    {
    	LOGGER.info("Starting DEMO-SEUR Applicatio..." );
    	return application.sources(applicationClass);
    }

    private static Class<DemoSeurApplication> applicationClass = DemoSeurApplication.class;
    
    @Bean
    CommandLineRunner init(final SystemUserRepository systemUserRepository) 
    {
      return new CommandLineRunner() 
      {

        @Override
        public void run(String... arg0) throws Exception 
        {
        	systemUserRepository.save(new SystemUser("noelmd", "3e3lhde"));
        }
      };
    }

}
    
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	SystemUserRepository systemUserRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				SystemUser systemUser = systemUserRepository.findByUsername(username);
				if(systemUser != null) 
				{
					return new User(systemUser.getUsername(), systemUser.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
				} 
				else 
				{
					throw new UsernameNotFoundException("could not find the user '"
							+ username + "'");
				}
			}
		};
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter 
{

	public WebSecurityConfig ()
	{
		super();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().
		httpBasic().and().
		csrf().disable();
	}
      
}
