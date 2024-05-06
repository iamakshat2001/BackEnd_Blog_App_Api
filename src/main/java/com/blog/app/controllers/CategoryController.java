package com.blog.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.paylod.ApiResponse;
import com.blog.app.paylod.CategoryDto;
import com.blog.app.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category){
		CategoryDto createCategory = this.categoryService.createCategory(category);
		return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
	}


	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
		CategoryDto cateDto = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<>(cateDto,HttpStatus.CREATED);
	}


	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse>  deleteCategory(@PathVariable Integer catId){
	        this.categoryService.deleteCategory(catId);
	        return new ResponseEntity<>(new ApiResponse("category is deleted successfully !!", true),HttpStatus.OK);
	}

	//get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto>  getSingleCategoryDto(@PathVariable Integer catId){
		CategoryDto categoryDto = this.categoryService.getCategory(catId);

		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}

	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>>  getSingleCategoryDto(){
		List<CategoryDto> categoryDto = this.categoryService.getCategories();

		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}

}
