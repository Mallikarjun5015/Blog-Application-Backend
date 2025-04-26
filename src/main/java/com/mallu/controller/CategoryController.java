package com.mallu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mallu.payloads.ApiResponse;
import com.mallu.payloads.CategoryDto;
import com.mallu.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService catService;
	
	@PostMapping("/saveCategory")
	public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody CategoryDto userDto) {

		CategoryDto saveUser = this.catService.saveCategory(userDto);
		return new ResponseEntity<CategoryDto>(saveUser, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto,
			@PathVariable("id") Integer categoryId) {

		CategoryDto updateCategory = this.catService.updateCategory(catDto, categoryId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id") Integer categoryId) {

		this.catService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted Successfully",true), HttpStatus.OK);
	}
	

	@GetMapping("/allcategories")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		return new ResponseEntity<>(this.catService.getAllCategories(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Integer catId) {
		return new ResponseEntity<CategoryDto>(this.catService.getCategoryById(catId), HttpStatus.OK);
	}
	
}
