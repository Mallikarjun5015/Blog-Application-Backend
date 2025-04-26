package com.mallu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallu.model.BlogUser;

public interface BlogUserRepository extends JpaRepository<BlogUser, Integer>{

	Optional<BlogUser> findByEmail(String email);
}
