package com.anurag.electronic_store.Elecsto.dtos;

import com.anurag.electronic_store.Elecsto.entities.CartItem;
import com.anurag.electronic_store.Elecsto.entities.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CartDto {
    private String cartId;
    private UserDto user;
    private List<CartItemDto> items = new ArrayList<>();
}
