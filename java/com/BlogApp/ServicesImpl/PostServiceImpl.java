package com.BlogApp.ServicesImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.BlogApp.Entities.Category;
import com.BlogApp.Entities.Post;
import com.BlogApp.Entities.User;
import com.BlogApp.Exception.ResourceNotFoundException;
import com.BlogApp.Payloads.PostDto;
import com.BlogApp.Payloads.PostResponse;
import com.BlogApp.Repository.CategoryRepository;
import com.BlogApp.Repository.PostRepository;
import com.BlogApp.Repository.UserRepository;
import com.BlogApp.Services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, UserRepository userRepository,
			CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post savePost = postRepository.save(post);
		PostDto responseDto = modelMapper.map(savePost, PostDto.class);
		return responseDto;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatePost = postRepository.save(post);
		return modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(Integer id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

		Sort sort = (sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = postRepository.findAll(pageable);

		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		List<Post> posts = postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		List<Post> posts = postRepository.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String key) {
		List<Post> posts = postRepository.searchByTitle(key);
		List<PostDto> postDtos = posts.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
