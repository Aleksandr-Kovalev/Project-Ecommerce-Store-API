package com.learning.embarkx.StoreAPI.service;

import com.learning.embarkx.StoreAPI.DTO.CategoryDTO;
import com.learning.embarkx.StoreAPI.Response.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
    CategoryDTO deleteCategory(Long categoryId);
}
