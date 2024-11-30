package com.BlogApp.ServicesImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.Entities.Comment;
import com.BlogApp.Entities.Post;
import com.BlogApp.Exception.ResourceNotFoundException;
import com.BlogApp.Payloads.CommentDto;
import com.BlogApp.Repository.CommentRepository;
import com.BlogApp.Repository.PostRepository;
import com.BlogApp.Services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.modelMapper=modelMapper;
	}

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment saveComment = commentRepository.save(comment);
		return modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		commentRepository.delete(comment);
	}

}
