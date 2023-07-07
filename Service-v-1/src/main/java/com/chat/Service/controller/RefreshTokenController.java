package com.chat.Service.controller;
/**
 * @author ali
 *
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.chat.Service.entity.UsersE;
import com.chat.Service.security.JWTUtile;
import com.chat.Service.service.UsersEService;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/refresh")
public class RefreshTokenController {

		
	private UsersEService usersEService;

	@Autowired
	public RefreshTokenController(UsersEService usersEService){
		this.usersEService = usersEService;
	}

	@GetMapping(path ="/Token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		String authorizationToken = request.getHeader("Authorization");
		if (authorizationToken != null && authorizationToken.startsWith(JWTUtile.BEARER)) {
			try {
				System.out.println(" arriver 1");
				String jwt = authorizationToken.substring(JWTUtile.BEARER.length());
				
				Algorithm algorithm = Algorithm.HMAC256(JWTUtile.SECRET);
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
				String username = decodedJWT.getSubject();
				//String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
				
				UsersE UE = usersEService.findByName(username);
				String jwtAccessToken = JWT.create()
						.withSubject(UE.getName())
						.withExpiresAt(new Date(System.currentTimeMillis()+JWTUtile.EXPIRE_ACCESS_TOKEN))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles", UE.getRoles().stream().map(r ->
							r.getRolesUser()
						).collect(Collectors.toList()))
						.sign(algorithm);
				
				Map<String, String> idToken = new HashMap<>();
				idToken.put("access-token", jwtAccessToken);
				idToken.put("refresh-token", jwt);
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), idToken);
				
				System.out.println(" authoriser");
			} catch (Exception e) {
				System.out.println(" authoriser malgree");
				Map<String, String> massage = new HashMap<>();
				massage.put("Message-error", e.getMessage());
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), massage);
			}
			System.out.println("Zonne d authoriser");
		}else {
				Map<String, String> massage = new HashMap<>();
				massage.put("Message-error", "Refresh Token required !!!");
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), massage);
			;
		}
	}

}
