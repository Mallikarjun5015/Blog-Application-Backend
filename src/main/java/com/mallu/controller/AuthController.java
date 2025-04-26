package com.mallu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mallu.exception.ApiException;
import com.mallu.payloads.BlogUserDto;
import com.mallu.payloads.JwtAuthRequest;
import com.mallu.payloads.JwtAuthResponse;
import com.mallu.security.JwtTokenHelper;
import com.mallu.service.BlogUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BlogUserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToke(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userdetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userdetails);

		JwtAuthResponse res = new JwtAuthResponse();

		res.setToken(token);

		return new ResponseEntity<JwtAuthResponse>(res, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Username or password...");

			throw new ApiException("Invalid Username or password...");
		}
	}

	// Register new user
	@PostMapping("/register")
	public ResponseEntity<BlogUserDto> registerUser(@Valid @RequestBody BlogUserDto userDto) {

		BlogUserDto newUser = this.userService.registerBlogUser(userDto);

		return new ResponseEntity<BlogUserDto>(newUser, HttpStatus.CREATED);
	}

}
