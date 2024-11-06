package com.anurag.electronic_store.Elecsto.services;


import com.anurag.electronic_store.Elecsto.dtos.CategoryDto;
import com.anurag.electronic_store.Elecsto.dtos.ProductDto;
import com.anurag.electronic_store.Elecsto.entities.Category;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    //update product
    ProductDto updateProduct(ProductDto productDto,String productId);

    //delete product
    void deleteProduct(String productId);

    //get all
    List<ProductDto> getAllProduct(int pageNumber , int pageSize , String sortBy , String sortDirection);

    //get Single
    ProductDto getProductById(String productId);

    //search by subtitle
    List<ProductDto> getProductBySubstring(String subString);


    //Create product under a category
    ProductDto createProductWithCategory(ProductDto productDto, String categoryId);

    //update category of a product
    ProductDto updateCategoryToExistingProduct(String productId , String categoryId);

    List<ProductDto> getAllProductsOfParticularCategory(String categoryId);
}
