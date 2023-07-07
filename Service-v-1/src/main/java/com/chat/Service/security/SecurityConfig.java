package com.chat.Service.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.chat.Service.filter.JwtAuthentificationFilter;
import com.chat.Service.filter.JwtAuthorizationFilter;

//import com.chat.Communicate.filters.JwtAuthentificationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService UserDetailsS;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(UserDetailsS);

	}


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "application/json", "application/x-www-form-urlencoded", "Access-Control-Allow-Methods", "Access-Control-Max-Age", "Access-Control-Allow-Headers"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	//@Bean
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	

		//------------------ quatriemes cas
		//http.cors().disable();
		http.csrf().disable()
		.cors().configurationSource(corsConfigurationSource()); // pour desactiver les securités d'authentification
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable(); /// pour desactiver les parrades fourni JWT
		http
	    .authorizeRequests()
	    	.antMatchers( "/api/v1/refresh/Token/**", "/login/**", "/api/v1/user/register/**","/api/v1/user/resendCode/**", "/api/v1/user/valideCompte/**").permitAll()
    	.and()
	    .authorizeRequests()
	        .anyRequest().authenticated(); /// pour prouver l'authetification 
			

		//---Pour verifier L'autorisation de l'utilisateur connecter
		//---Celui-ci est utile dans les consultation des donnes sencibles qui ne consiste pas l'authentification
		//---d'ou vous pouvez le commenter si vous utiliser seulement l'authentification de l'utilisateur
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); 
			
		// Verifier L'authentification de l'utilisateur et lui fournir des access et son role(s)
		http.addFilter(new JwtAuthentificationFilter(authenticationManagerBean())); 
		
	
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
