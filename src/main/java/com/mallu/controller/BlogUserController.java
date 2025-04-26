package com.mallu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mallu.payloads.ApiResponse;
import com.mallu.payloads.BlogUserDto;
import com.mallu.service.BlogUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class BlogUserController {

	@Autowired
	private BlogUserService userService;

	@PostMapping("/saveUser")
	public ResponseEntity<BlogUserDto> saveBlogUser(@Valid @RequestBody BlogUserDto userDto) {

		BlogUserDto saveUser = this.userService.saveBlogUser(userDto);
		return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BlogUserDto> updateBlogUser(@Valid @RequestBody BlogUserDto userDto,
			@PathVariable("id") Integer userId) {

		BlogUserDto updateUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteBlogUser(@PathVariable("id") Integer userId) {
		System.out.println("Inside controller");

		this.userService.deleteUser(userId);
		System.out.println("Inside delete controller");

		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfull", true), HttpStatus.OK);
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<BlogUserDto>> getAllBlogUsers() {
		return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<BlogUserDto> getUserById(@PathVariable Integer userId) {
		return new ResponseEntity<>(this.userService.getUserbyId(userId), HttpStatus.OK);
	}
}
