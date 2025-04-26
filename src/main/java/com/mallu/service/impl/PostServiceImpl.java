package com.mallu.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mallu.exception.ResourceNotfoundException;
import com.mallu.model.BlogUser;
import com.mallu.model.Category;
import com.mallu.model.Post;
import com.mallu.payloads.PostDto;
import com.mallu.payloads.PostResponse;
import com.mallu.repository.BlogUserRepository;
import com.mallu.repository.CategoryRepository;
import com.mallu.repository.PostRepository;
import com.mallu.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BlogUserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	
	@Override
	public PostDto savePost(PostDto postDto,Integer userId, Integer categoryId) {
		
		BlogUser user = this.userRepo.findById(userId)
		.orElseThrow(()-> new ResourceNotfoundException("user", "userId", userId));
		
		Category cat = this.categoryRepo.findById(categoryId)
		.orElseThrow(()-> new ResourceNotfoundException("Category", "CategoryId", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		
		Post savePost=this.postRepo.save(post);
		
		return this.modelMapper.map(savePost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotfoundException("Post", "PostId", postId));
				
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId)
		.orElseThrow(()-> new ResourceNotfoundException("Post", "PostId", postId));
		
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse  getAllPost(int pageSize ,int pageNumber, String sortBy,String sortDir) {
		
		Sort sort = null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort= Sort.by(sortBy).ascending();
		}else {
			sort= Sort.by(sortBy).descending();
		}
		
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(page);
		
		List<Post> allPosts = pagePost.getContent();
		
		
		List<PostDto> postDtos= allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResp = new PostResponse();
		
		 postResp.setContent(postDtos);
		 postResp.setPageNumber(pagePost.getNumber());
		 postResp.setPageSize(pagePost.getSize());
		 postResp.setTotalElements(pagePost.getTotalElements());
		 
		 postResp.setTotalPages(pagePost.getTotalPages());
		 postResp.setLastPage(pagePost.isLast());
		
		return postResp;
	}

	@Override
	public PostDto getPostById(Integer id) {
		Post post =this.postRepo.findById(id)
		.orElseThrow(()-> new ResourceNotfoundException("Post", "PostId",  id));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotfoundException("Category", "CategoryId", categoryId));
		
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDto =posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return  postDto;
	}

	@Override
	public List<PostDto> getPostsByBlogUer(Integer userId) {
		BlogUser user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotfoundException("User", "UserId", userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDto =posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		return  postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		 List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		 
			List<PostDto> postDto =posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}
