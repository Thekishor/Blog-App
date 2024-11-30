package com.BlogApp.Services;

import com.BlogApp.Payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
}
