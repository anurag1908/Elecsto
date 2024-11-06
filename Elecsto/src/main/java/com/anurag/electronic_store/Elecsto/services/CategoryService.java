package com.anurag.electronic_store.Elecsto.services;

import com.anurag.electronic_store.Elecsto.dtos.CategoryDto;
import com.anurag.electronic_store.Elecsto.dtos.UserDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    //update user
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

    //delete user
    void deleteCategory(String categoryId);

    //get all users
    List<CategoryDto> getAllCategory(int pageNumber , int pageSize , String sortBy , String sortDirection);

    //get Single User
    CategoryDto getCategoryById(String categoryId);
}
