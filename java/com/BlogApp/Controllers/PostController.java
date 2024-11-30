package com.BlogApp.Controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.BlogApp.Config.AppConstants;
import com.BlogApp.Payloads.ApiResponse;
import com.BlogApp.Payloads.PostDto;
import com.BlogApp.Payloads.PostResponse;
import com.BlogApp.Services.FileService;
import com.BlogApp.Services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	public PostController(PostService postService, FileService fileService) {
		this.postService = postService;
		this.fileService = fileService;
	}

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto postDtoInfo = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(postDtoInfo, HttpStatus.CREATED);
	}

	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		List<PostDto> postDtos = postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postDtos = postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// Get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDirection) {
		PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	// get post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postDto = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	// delete by id
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is successfully deleted !!", true), HttpStatus.OK);
	}

	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// search post by title
	@GetMapping("/posts/search/{key}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String key) {
		List<PostDto> postDtos = postService.searchPosts(key);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// post image upload
	@PostMapping("/post/image/upload/{id}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile multipartFile,
			@PathVariable Integer id) throws IOException {
		String fileName = fileService.uploadImage(path, multipartFile);
		PostDto postDto = postService.getPostById(id);
		postDto.setImageName(fileName);
		PostDto updatePostDto = this.postService.updatePost(postDto, id);
		return new ResponseEntity<PostDto>(updatePostDto,HttpStatus.OK);
	}
}
