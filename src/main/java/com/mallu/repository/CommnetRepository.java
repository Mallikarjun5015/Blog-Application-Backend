package com.mallu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallu.model.Comment;

public interface CommnetRepository extends JpaRepository<Comment, Integer> {

}
