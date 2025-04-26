package com.mallu.service;

import com.mallu.payloads.CommentDto;

public interface CommentService {

	
	CommentDto createCommnet(CommentDto commentDto, Integer postId);
	
	void deleteCommnet(Integer commnetId);
	
	
}
