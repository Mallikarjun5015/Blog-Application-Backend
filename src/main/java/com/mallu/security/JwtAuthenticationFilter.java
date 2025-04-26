package com.mallu.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// step 01: Getting the token 1st

		String requestToken = request.getHeader("Authorization");

		System.out.println(requestToken); // It will be in the format " Beare 243424fgbv"

		String username = null;

		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer ")) {

			token = requestToken.substring(7).trim();// after Bearer_

			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
				
				System.out.println("Username extracted from token: " + username);
				
			} catch (ExpiredJwtException e) {
			    System.err.println("JWT expired: " + e.getMessage());
			} catch (MalformedJwtException e) {
			    System.err.println("Malformed JWT: " + e.getMessage());
			} catch (Exception e) {
			    System.err.println("Token parsing error: " + e.getMessage());
			}

		} else {
			System.out.println("JWT Token doesn't starts with Bearer");
		}

		// Step 02: Validate the Token which we got

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

	        if (this.jwtTokenHelper.validateToken(token)) {
	            UsernamePasswordAuthenticationToken authToken =
	                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authToken);
	        }
	    else {
				System.out.println("invalid JWT token ");
			}
		} else {
			System.out.println("UserName is null or context is now null ");
		}
		
		filterChain.doFilter(request, response);

	}

}
