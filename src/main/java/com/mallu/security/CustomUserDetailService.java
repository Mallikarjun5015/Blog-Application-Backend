package com.mallu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mallu.exception.ResourceNotfoundException;
import com.mallu.model.BlogUser;
import com.mallu.repository.BlogUserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private BlogUserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// loading user from the database by username
		System.out.println(username);
		
		BlogUser user=	this.userRepo.findByEmail(username)
		.orElseThrow(()-> new ResourceNotfoundException("BlogUser", "email: "+username, 0));
		
		return user;
	}

}
