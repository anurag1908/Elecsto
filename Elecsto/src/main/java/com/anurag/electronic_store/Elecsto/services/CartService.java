package com.anurag.electronic_store.Elecsto.services;

import com.anurag.electronic_store.Elecsto.dtos.AddItemToCartRequest;
import com.anurag.electronic_store.Elecsto.dtos.CartDto;
import com.anurag.electronic_store.Elecsto.entities.Cart;

public interface CartService {
    //add items to cart :
    //case-1 : if cart for a user is not present : create cart for that user and then add item to cart
    // &
    //case-2 : if cart is available add item to cart
    CartDto addItemToCart(String userId , AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId, int cartItemId);

    //remove all items from cart
    void deleteCart(String userId);

    //fetch user cart
    CartDto getCartOfUser(String userId);

}
