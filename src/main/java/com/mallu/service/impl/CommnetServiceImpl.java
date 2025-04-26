package com.mallu.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallu.exception.ResourceNotfoundException;
import com.mallu.model.Comment;
import com.mallu.model.Post;
import com.mallu.payloads.CommentDto;
import com.mallu.repository.CommnetRepository;
import com.mallu.repository.PostRepository;
import com.mallu.service.CommentService;

@Service
public class CommnetServiceImpl implements CommentService{

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommnetRepository commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createCommnet(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotfoundException("post", "postId", postId));
		
		Comment commnet = this.modelMapper.map(commentDto, Comment.class);
		
		commnet.setPost(post);
		
		Comment saveComment =this.commentRepo.save(commnet);
		
		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteCommnet(Integer commentId) {
		
		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(()->new ResourceNotfoundException("Comment","commentId", commentId));
		
		this.commentRepo.delete(com);
	}

}
