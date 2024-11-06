package com.anurag.electronic_store.Elecsto.controllers;


import com.anurag.electronic_store.Elecsto.dtos.ApiResponseMessage;
import com.anurag.electronic_store.Elecsto.dtos.ImageResponse;
import com.anurag.electronic_store.Elecsto.dtos.ProductDto;
import com.anurag.electronic_store.Elecsto.dtos.UserDto;
import com.anurag.electronic_store.Elecsto.services.FileService;
import com.anurag.electronic_store.Elecsto.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imageUploadPath;

    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        ProductDto product = productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateUser(@Valid @RequestBody ProductDto productDto , @PathVariable("productId") String productId ){
        ProductDto updatedProductDto = productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(updatedProductDto,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String productId){
        productService.deleteProduct(productId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Deleted Successfully ")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection

    ){
        return new ResponseEntity<>(productService.getAllProduct(pageNumber,pageSize,sortBy,sortDirection), HttpStatus.OK);
    }

    //get single by id
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    //get single by subSting
    @GetMapping("/search/{subString}")
    public ResponseEntity<List<ProductDto>> getBySubstring(@PathVariable String subString){
        //write your code here
        List<ProductDto> products = productService.getProductBySubstring(subString);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("productImage") MultipartFile image,
            @PathVariable String productId
    ) throws IOException
    {
        String imageName = fileService.uploadImage(image,imageUploadPath);

        ProductDto product = productService.getProductById(productId);
        product.setImageName(imageName);
        ProductDto updatedProduct = productService.updateProduct(product,productId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(updatedProduct.getImageName())
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Uploaded")
                .build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
}