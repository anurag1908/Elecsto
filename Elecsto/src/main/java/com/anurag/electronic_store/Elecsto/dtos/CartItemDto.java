package com.anurag.electronic_store.Elecsto.dtos;

import com.anurag.electronic_store.Elecsto.entities.Cart;
import com.anurag.electronic_store.Elecsto.entities.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CartItemDto {

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;
}
