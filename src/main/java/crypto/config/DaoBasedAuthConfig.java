package crypto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;





	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@Configuration
	@EnableWebSecurity
	
	public class DaoBasedAuthConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private BCryptPasswordEncoder encoder;
		@Autowired
		private UserDetailsService userDetailsService;
		
		
		@Bean
		public AuthenticationProvider provider() {
			System.out.println("In Authentication Provider");
			DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
			daoProvider.setUserDetailsService(userDetailsService);
			daoProvider.setPasswordEncoder(encoder);
			return daoProvider;
		}
		
		  @Autowired 
		  protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
			  System.out.println("In auth builder " + auth);
			  PasswordEncoderFactories.createDelegatingPasswordEncoder();
			  auth.inMemoryAuthentication().withUser("praful").password(encoder.encode("389019")).roles("ADMIN");
			  auth.authenticationProvider(provider());
		  }
		
		

		

	/*	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
		<sec:csrfInput /> */
		
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			System.out.println("In Request Configurer!");
			http.authorizeRequests().anyRequest().authenticated().and().formLogin().permitAll()
					.and().logout().permitAll();

			http.csrf().disable();//disable this once the goddamn taglib is added!
		}
		
		 
		

		


	}