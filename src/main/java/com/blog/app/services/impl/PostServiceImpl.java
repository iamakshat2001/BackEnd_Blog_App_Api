package com.blog.app.services.impl;

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

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourseNotFoundException;
import com.blog.app.paylod.PostDto;
import com.blog.app.paylod.PostResponse;
import com.blog.app.repositories.CategoryRepo;
import com.blog.app.repositories.PostRepo;
import com.blog.app.repositories.UserRepo;
import com.blog.app.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId,Integer categoryId) {

		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourseNotFoundException("User","User Id",userId));

		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("Category","CategoryId",categoryId));

	    Post post = this.modelMapper.map(postDto, Post.class);
	    post.setImageName("default.png");
	    post.setAddedDate(new Date());
	    post.setUser(user);
	    post.setCategory(category);

	    Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
	   Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourseNotFoundException("Post","Post Id",postId));

	   post.setTitle(postDto.getTitle());
	   post.setContent(postDto.getContent());
	   post.setImageName(postDto.getImageName());

	   Post updatedPost = this.postRepo.save(post);

		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourseNotFoundException("Post","PostId",postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(p);

		List<Post> allPosts = pagePost.getContent();


		List<PostDto> postDtos  = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getNumberOfElements());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
	    Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourseNotFoundException("Post","Post Id",postId));
		PostDto postDto = this.modelMapper.map(post,PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourseNotFoundException("category","categoryId",categoryId));
		System.out.println(cat);
		List<Post> posts = this.postRepo.findByCategory(cat);

		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourseNotFoundException("user","userId",userId));
		List<Post> posts = this.postRepo.findByUser(user);


		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts= this.postRepo.findByTitleContaining(keyword);
	    List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}


}
