package com.gr.inv.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	   @Autowired
	   private CustomAuthenticationEntryPoint authenticationEntryPoint;

	   @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	          .withUser("user1").password("user1Pass")
	          .authorities("ROLE_USER");
	        
	        auth.inMemoryAuthentication()
	          .withUser("user2").password("user2Pass")
	          .authorities("ROLE_USER");
	    }
	   
	   
//	   @Override
//	    protected void configure(HttpSecurity http) throws Exception {
//	        http
//	            .authorizeRequests()
//	                .antMatchers("/public").permitAll()
//	                .anyRequest().authenticated()
//	                .and()
//	                .formLogin()
//	                .permitAll()
//	                .and()
//	            .logout()
//	                .permitAll();;
//	    }

	   
	   @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	                .antMatchers("/inventoryList").permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .httpBasic()
	                .authenticationEntryPoint(authenticationEntryPoint);
	    }	   
	   
}
