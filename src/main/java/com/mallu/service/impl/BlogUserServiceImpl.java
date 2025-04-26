package com.mallu.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mallu.model.BlogUser;
import com.mallu.model.Role;
import com.mallu.config.AppConstatns;
import com.mallu.exception.*;
import com.mallu.payloads.BlogUserDto;
import com.mallu.repository.BlogUserRepository;
import com.mallu.repository.RoleRepository;
import com.mallu.service.BlogUserService;

@Service
public class BlogUserServiceImpl implements BlogUserService {

	@Autowired
	private BlogUserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEnocder;

	@Override
	public BlogUserDto saveBlogUser(BlogUserDto userDto) {

		BlogUser user = this.dtoToBlogUser(userDto);
		BlogUser saveUser = this.userRepo.save(user);

		return this.userToDto(saveUser);
	}

	@Override
	public BlogUserDto updateUser(BlogUserDto userDto, Integer userId) {

		BlogUser user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotfoundException("BlogUser", "id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());

		BlogUser updatedUSer = this.userRepo.save(user);
		BlogUserDto userDto1 = this.userToDto(updatedUSer);

		return userDto1;
	}

	@Override
	public BlogUserDto getUserbyId(Integer userId) {

		BlogUser user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotfoundException("BlogUser", "id", userId));

		return this.userToDto(user);
	}

	@Override
	public List<BlogUserDto> getAllUsers() {

		List<BlogUser> users = this.userRepo.findAll();

		List<BlogUserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		BlogUser user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotfoundException("BlogUser", "id", userId));

		this.userRepo.delete(user);
	}

	private BlogUser dtoToBlogUser(BlogUserDto userDto) {

		BlogUser user = this.modelMapper.map(userDto, BlogUser.class);
		return user;
	}

	public BlogUserDto userToDto(BlogUser user) {

		BlogUserDto userDto = this.modelMapper.map(user, BlogUserDto.class);
		return userDto;
	}

	@Override
	public BlogUserDto registerBlogUser(BlogUserDto userDto) {

		BlogUser user = this.modelMapper.map(userDto, BlogUser.class);

		// password encoded..
		user.setPassword(this.passwordEnocder.encode(user.getPassword()));
		
		// rolse setting
		Role role =this.roleRepo.findById(AppConstatns.NORMAL_USER).get();
		user.getRoles().add(role);
		
		BlogUser newUser =this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, BlogUserDto.class);
	}

}
