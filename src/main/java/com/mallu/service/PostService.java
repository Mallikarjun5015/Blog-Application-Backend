package com.mallu.service;

import java.util.List;

import com.mallu.model.Post;
import com.mallu.payloads.PostDto;
import com.mallu.payloads.PostResponse;

public interface PostService {

	PostDto savePost(PostDto psoyDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto psoyDto, Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse  getAllPost(int pageSize ,int pageNumber,String sortBy, String sortDir);
	
	PostDto getPostById(Integer id);
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	List<PostDto> getPostsByBlogUer(Integer userId);
	
	List<PostDto> searchPosts(String keyword);
	
}
