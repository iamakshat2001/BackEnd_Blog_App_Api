package com.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entities.Category;
import com.blog.app.exceptions.ResourseNotFoundException;
import com.blog.app.paylod.CategoryDto;
import com.blog.app.repositories.CategoryRepo;
import com.blog.app.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	// method for inserting single category into table

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category cat = this.modelMapper.map(categoryDto, Category.class);

		Category addedCat = this.categoryRepo.save(cat);


		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	//method for updating single data into database table
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("category","categoryId",categoryId));

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());

		this.categoryRepo.save(cat);
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	//method for deleting single category by unsing id
	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("category","category id",categoryId));

		this.categoryRepo.delete(cat);

	}


    //method for getting single category by id
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("category","category id",categoryId));
		CategoryDto catDto = this.modelMapper.map(cat, CategoryDto.class);

		return catDto;
	}


	 //method for getting all category
	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categorisDtos = categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());


		for(CategoryDto c:categorisDtos ) {
			System.out.println(c.getCategoryId());
			System.out.println(c.getCategoryTitle());
			System.out.println(c.getCategoryDescription());
		}

		return categorisDtos;
	}


}
