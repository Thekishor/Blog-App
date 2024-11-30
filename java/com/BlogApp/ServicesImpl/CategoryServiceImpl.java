package com.BlogApp.ServicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApp.Entities.Category;
import com.BlogApp.Exception.ResourceNotFoundException;
import com.BlogApp.Payloads.CategoryDto;
import com.BlogApp.Repository.CategoryRepository;
import com.BlogApp.Services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category saveCategory = categoryRepository.save(category);
		return modelMapper.map(saveCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategor(CategoryDto categoryDto, Integer id) {
		Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updateCategory = categoryRepository.save(category);
		return modelMapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer id) {
		Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
		categoryRepository.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map((category)-> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
