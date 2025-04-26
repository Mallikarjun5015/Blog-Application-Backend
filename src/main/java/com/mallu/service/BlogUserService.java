package com.mallu.service;

import java.util.List;

import com.mallu.payloads.BlogUserDto;

public interface BlogUserService {

	BlogUserDto registerBlogUser(BlogUserDto user);
	
	BlogUserDto saveBlogUser(BlogUserDto user);
	
	BlogUserDto updateUser(BlogUserDto user, Integer userId);
	
	BlogUserDto getUserbyId(Integer userId);
	
	List<BlogUserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
}
