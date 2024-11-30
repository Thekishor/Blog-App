package com.BlogApp.Services;

import java.util.List;

import com.BlogApp.Payloads.PostDto;
import com.BlogApp.Payloads.PostResponse;

public interface PostService {
	
	public PostDto createPost(PostDto postDto, Integer userId , Integer categoryId);
	public PostDto updatePost(PostDto postDto, Integer id);
	public void deletePost(Integer id);
	public PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDirection);
	public PostDto getPostById(Integer id);
	public List<PostDto> getPostByCategory(Integer id);
	public List<PostDto> getPostByUser(Integer id);
	public List<PostDto> searchPosts(String key);
}
