package com.mallu.service;

import java.util.List;

import com.mallu.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto saveCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	public void deleteCategory(Integer categoryId);
	
	CategoryDto getCategoryById(Integer categoryId);
	
	List<CategoryDto> getAllCategories();
}
