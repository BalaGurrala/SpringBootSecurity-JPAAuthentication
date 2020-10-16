package com.example.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class CustomSecurity extends WebSecurityConfigurerAdapter {
	
	/*@Autowired
	private DataSource dataSource;*/
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("configure(AuthenticationManagerBuilder auth)");
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			/*.httpBasic() 
			.and()*/
			.authorizeRequests()
			.antMatchers("/user").hasRole("USER")
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/courses/**").hasRole("USER")
			.antMatchers(HttpMethod.DELETE, "/courses/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/courses").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/courses").hasRole("ADMIN")
			.antMatchers("/**").permitAll()
			.and().csrf().disable()
			.formLogin();

	}
}
