package com.BlogApp.Controllers;

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

import com.BlogApp.Payloads.ApiResponse;
import com.BlogApp.Payloads.CategoryDto;
import com.BlogApp.Services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto categoryDtoInfo = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(categoryDtoInfo,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto , @PathVariable Integer id){
		CategoryDto updateCategoryDto = categoryService.updateCategor(categoryDto, id);
		return new ResponseEntity<CategoryDto>(updateCategoryDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
		categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id){
		CategoryDto categoryDto = categoryService.getCategoryById(id);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categoryDtos = categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);
	}
}
