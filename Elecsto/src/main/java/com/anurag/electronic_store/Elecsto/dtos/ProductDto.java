package com.anurag.electronic_store.Elecsto.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {


    private String productId;

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int quantity;

    private Date dateAdded;

    private boolean inStock;

    private String imageName;

    private CategoryDto category;

}
