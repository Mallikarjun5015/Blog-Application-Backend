package com.mallu.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallu.exception.ResourceNotfoundException;
import com.mallu.model.Category;
import com.mallu.payloads.CategoryDto;
import com.mallu.repository.CategoryRepository;
import com.mallu.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modeMapper;
	
	@Override
	public CategoryDto saveCategory(CategoryDto categoryDto) {
		
		Category cat = this.modeMapper.map(categoryDto, Category.class);
		Category saveUser = this.categoryRepo.save(cat);
		
		return this.modeMapper.map(saveUser, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotfoundException("Category", "CategoryId", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCat = this.categoryRepo.save(cat);
		
		return this.modeMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotfoundException("Category", "CategoryId", categoryId));
		
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotfoundException("Category", "CategoryId", categoryId));
		
		return this.modeMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> cat = this.categoryRepo.findAll();	
		List<CategoryDto> catDto = cat.stream().map((category)->this.modeMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		return catDto;
	}

}
