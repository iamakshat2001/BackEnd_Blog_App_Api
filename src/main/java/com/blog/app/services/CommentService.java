package com.blog.app.services;

import com.blog.app.paylod.CommentDto;

public interface CommentService {


	// create comment
	CommentDto createComment(CommentDto commentDto, Integer postId);


	// Delete Comment
	void deleteComment(Integer commentId);
}
