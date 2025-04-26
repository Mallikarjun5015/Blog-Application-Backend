package com.mallu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallu.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
