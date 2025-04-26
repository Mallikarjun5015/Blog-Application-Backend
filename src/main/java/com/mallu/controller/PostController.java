package com.mallu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.mallu.config.AppConstatns;
import com.mallu.payloads.ApiResponse;
import com.mallu.payloads.PostDto;
import com.mallu.payloads.PostResponse;
import com.mallu.service.FileService;
import com.mallu.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> saveCategory(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto saveUser = this.postService.savePost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(saveUser, HttpStatus.CREATED);
	}

	// by user
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostsByUSer(@PathVariable Integer userId) {
		List<PostDto> posts = this.postService.getPostsByBlogUer(userId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	// by category

	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	@GetMapping("allPost")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstatns.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstatns.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstatns.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstatns.SORT_DIR, required = false) String sortDir)

	{
		PostResponse posts = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

		PostDto post = this.postService.getPostById(postId);

		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {

		PostDto updatePost = this.postService.updatePost(postDto, postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {

		this.postService.deletePost(postId);

		return new ApiResponse("Post is Successfully Deleted..", true);
	}

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> serachpPostByTitle(@PathVariable("keywords") String keywords) {

		List<PostDto> result = this.postService.searchPosts(keywords);

		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}

	// image Upload of post
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {

		PostDto postDto = this.postService.getPostById(postId);

		if (postDto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String fileName = this.fileService.uploadImage(path, image);

		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
	@PathVariable("imageName") String imageName,
	HttpServletResponse response

	) throws IOException {
		
	InputStream resource = this.fileService.getResource(path, imageName);
	response.setContentType(MediaType. IMAGE_JPEG_VALUE);
	StreamUtils.copy(resource, response.getOutputStream());
	
	}

}
