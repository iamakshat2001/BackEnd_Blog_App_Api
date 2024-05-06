package com.blog.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.paylod.ApiResponse;
import com.blog.app.paylod.CommentDto;
import com.blog.app.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId)
	{
		CommentDto createComment  = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<>(createComment, HttpStatus.OK);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);

		return new ResponseEntity<>(new ApiResponse("Comment Deleted SuccessFully !!",true),HttpStatus.OK);
	}
}