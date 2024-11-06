package com.anurag.electronic_store.Elecsto.services.implementation;

import com.anurag.electronic_store.Elecsto.dtos.CategoryDto;
import com.anurag.electronic_store.Elecsto.dtos.UserDto;
import com.anurag.electronic_store.Elecsto.entities.Category;
import com.anurag.electronic_store.Elecsto.entities.User;
import com.anurag.electronic_store.Elecsto.exceptions.ResourceNotFoundException;
import com.anurag.electronic_store.Elecsto.repositories.CategoryRepository;
import com.anurag.electronic_store.Elecsto.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String id = UUID.randomUUID().toString();
        categoryDto.setCategoryId(id);
        Category category = dtoToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        CategoryDto ourDto = entityToDto(savedCategory);
        return ourDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category doesn't exist! "));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return entityToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category doesn't exist! "));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategory(int pageNumber , int pageSize , String sortBy , String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> pageOfCategory = categoryRepository.findAll(pageable);
        List<CategoryDto> dtoList = new ArrayList<>();
        for (Category category : pageOfCategory) {
            dtoList.add(entityToDto(category));
        }

        return dtoList;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category doesn't exist! "));
        return entityToDto(category);
    }



    private CategoryDto entityToDto(Category category) {
        return mapper.map(category,CategoryDto.class);
    }
    private Category dtoToEntity(CategoryDto categoryDto) {
        return mapper.map(categoryDto,Category.class);
    }
}
