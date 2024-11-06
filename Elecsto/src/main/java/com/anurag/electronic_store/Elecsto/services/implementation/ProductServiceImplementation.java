package com.anurag.electronic_store.Elecsto.services.implementation;

import com.anurag.electronic_store.Elecsto.dtos.CategoryDto;
import com.anurag.electronic_store.Elecsto.dtos.ProductDto;
import com.anurag.electronic_store.Elecsto.entities.Category;
import com.anurag.electronic_store.Elecsto.entities.Product;
import com.anurag.electronic_store.Elecsto.exceptions.ResourceNotFoundException;
import com.anurag.electronic_store.Elecsto.repositories.CategoryRepository;
import com.anurag.electronic_store.Elecsto.repositories.ProductRepository;
import com.anurag.electronic_store.Elecsto.services.ProductService;
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
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        Product product = dtoToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        ProductDto ourDto = entityToDto(savedProduct);
        return ourDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product doesn't exist! "));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setInStock(productDto.isInStock());
        product.setImageName(productDto.getImageName());
        Product updatedProduct = productRepository.save(product);
        return entityToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product doesn't exist! "));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> pageOfCategory = productRepository.findAll(pageable);
        List<ProductDto> dtoList = new ArrayList<>();
        for (Product product : pageOfCategory) {
            dtoList.add(entityToDto(product));
        }

        return dtoList;
    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found with given ID!"));
        return entityToDto(product);
    }

    @Override
    public List<ProductDto> getProductBySubstring(String subString) {
        List<Product> products = productRepository.findByTitleContaining(subString);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(entityToDto(product));
        }
        return productDtos;

    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        Product product = dtoToEntity(productDto);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        ProductDto ourDto = entityToDto(savedProduct);
        return ourDto;


    }

    @Override
    public ProductDto updateCategoryToExistingProduct(String productId, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product doesn't exist! "));
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        return entityToDto(updatedProduct);
    }

    @Override
    public List<ProductDto> getAllProductsOfParticularCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        List<Product> products = productRepository.findByCategory(category);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(entityToDto(product));
        }
        return productDtos;
    }


    private ProductDto entityToDto(Product product) {
        return mapper.map(product,ProductDto.class);
    }
    private Product dtoToEntity(ProductDto productDto) {
        return mapper.map(productDto,Product.class);
    }
}
