package com.chat.Service.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chat.Service.security.JWTUtile;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter{
	
	//@Autowired
	private AuthenticationManager authenticationManager; 
	
	public JwtAuthentificationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("attemptAuthentication-user");
		// Envoie des donnée par la Methode classique d'authetification
		String userName = request.getParameter("username");
		String userPassword = request.getParameter("password");
		System.out.println(request.getParameter("username"));
		System.out.println(request.getParameter("password"));
		
		UsernamePasswordAuthenticationToken authentificationToken =
				new UsernamePasswordAuthenticationToken(userName, userPassword);
		//System.out.println(authentificationToken);
		
		
		// Envoie des donnée sous forma JSON pour l'authetification
		return authenticationManager.authenticate(authentificationToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		//System.out.println("successfulAuthentication");
		
		User user = (User) authResult.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256(JWTUtile.SECRET);
		
		String jwtAccessToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JWTUtile.EXPIRE_ACCESS_TOKEN))
				.withIssuer(request.getRequestURI().toString())
				.withClaim("roles", user.getAuthorities().stream().map(ga ->
					ga.getAuthority()
				).collect(Collectors.toList()))
				.sign(algorithm);
		//response.setHeader("Authorization", jwtAccessToken);
		
		String jwtRefrecheTOkenToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JWTUtile.EXPIRE_REFRESH_TOKEN))
				.withIssuer(request.getRequestURI().toString())
				.sign(algorithm);
		
		Map<String, String> idToken = new HashMap<>();
		idToken.put("access-token", jwtAccessToken);
		idToken.put("refresh-token", jwtRefrecheTOkenToken);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), idToken);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		Map<String, String> idException = new HashMap<>();
		idException.put("access-token", "null" );
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), idException);
	}
}
