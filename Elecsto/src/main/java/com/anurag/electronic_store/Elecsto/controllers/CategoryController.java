package com.anurag.electronic_store.Elecsto.controllers;

import com.anurag.electronic_store.Elecsto.dtos.ApiResponseMessage;
import com.anurag.electronic_store.Elecsto.dtos.CategoryDto;
import com.anurag.electronic_store.Elecsto.dtos.ProductDto;
import com.anurag.electronic_store.Elecsto.dtos.UserDto;
import com.anurag.electronic_store.Elecsto.services.CategoryService;
import com.anurag.electronic_store.Elecsto.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    //create category
    @PostMapping
    public ResponseEntity<CategoryDto> createUser(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }

    //update category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto , @PathVariable("categoryId") String categoryId ){
        CategoryDto category = categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    //delete category
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        categoryService.deleteCategory(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Deleted Successfully ")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }


    //Get all category
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection

    ){
        return new ResponseEntity<>(categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortDirection), HttpStatus.OK);
    }


    //get single category by id
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId){
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    //create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto
    ){
        ProductDto product = productService.createProductWithCategory(productDto,categoryId);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    //update product category
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateProductCategory(
            @PathVariable("categoryId") String categoryId,
            @PathVariable("productId") String productId
    ){
        ProductDto productDto = productService.updateCategoryToExistingProduct(productId,categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    //get all products of a category
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsOfParticularCategory(
            @PathVariable("categoryId") String categoryId
    ){
        List<ProductDto> productDto = productService.getAllProductsOfParticularCategory(categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

}
