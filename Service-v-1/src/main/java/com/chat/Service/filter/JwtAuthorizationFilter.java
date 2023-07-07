package com.chat.Service.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.chat.Service.security.JWTUtile;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (
			request.getServletPath().equals(JWTUtile.LOGIN) 
			|| 
			request.getServletPath().equals(JWTUtile.REFRESH_TOKEN) 
			|| 
			request.getServletPath().equals(JWTUtile.REGISTER_USER)
			||
			request.getServletPath().equals(JWTUtile.RESEND_EMAIL_USER)
			||
			request.getServletPath().equals(JWTUtile.VALIDE_USER)
		) {
			filterChain.doFilter(request, response);
		}else {
			String authorizationToken = request.getHeader(JWTUtile.AUTH_HEADER);
			if (authorizationToken != null && authorizationToken.startsWith(JWTUtile.BEARER)) {
				try {
					String jwt = authorizationToken.substring(JWTUtile.BEARER.length());
					Algorithm algorithm = Algorithm.HMAC256(JWTUtile.SECRET);
					JWTVerifier jwtVerifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					
					Collection<GrantedAuthority> authorities = new ArrayList<>();
					for (String r:roles) {
						authorities.add(new SimpleGrantedAuthority(r));
					}
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(username, null, authorities);
					
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
					System.out.println("Acces-token authoriser");
				} catch (Exception e) {
					//System.out.println("Acces-token authoriser malgree");
					Map<String, String> message = new HashMap<>();
					message.put("Message-Error", e.getMessage());
					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), message);
				}
				//System.out.println("Zonne Acces-token d authoriser");
			}else {
				System.out.println("Non authoriser l'acces");
				Map<String, String> massage = new HashMap<>();
				massage.put("Message-error", "Acces Token required !!!");
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), massage);
			}
		}

	}

}
