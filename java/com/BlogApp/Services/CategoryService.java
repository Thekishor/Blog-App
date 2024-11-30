package com.BlogApp.Services;

import java.util.List;

import com.BlogApp.Payloads.CategoryDto;

public interface CategoryService {
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	public CategoryDto updateCategor(CategoryDto categoryDto, Integer id);
	public void deleteCategory(Integer id);
	public CategoryDto getCategoryById(Integer id);
	public List<CategoryDto> getAllCategory();
}
