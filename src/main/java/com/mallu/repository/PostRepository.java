package com.mallu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mallu.model.BlogUser;
import com.mallu.model.Category;
import com.mallu.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(BlogUser user);

	List<Post> findByCategory(Category category);
	
	//List<Post>  findByTitleContaining(String title);
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);

}
