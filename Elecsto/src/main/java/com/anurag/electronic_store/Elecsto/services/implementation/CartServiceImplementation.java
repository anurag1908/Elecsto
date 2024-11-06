package com.anurag.electronic_store.Elecsto.services.implementation;

import com.anurag.electronic_store.Elecsto.dtos.AddItemToCartRequest;
import com.anurag.electronic_store.Elecsto.dtos.CartDto;
import com.anurag.electronic_store.Elecsto.entities.Cart;
import com.anurag.electronic_store.Elecsto.entities.CartItem;
import com.anurag.electronic_store.Elecsto.entities.Product;
import com.anurag.electronic_store.Elecsto.entities.User;
import com.anurag.electronic_store.Elecsto.exceptions.BadApiRequestException;
import com.anurag.electronic_store.Elecsto.exceptions.ResourceNotFoundException;
import com.anurag.electronic_store.Elecsto.repositories.CartItemRepository;
import com.anurag.electronic_store.Elecsto.repositories.CartRepository;
import com.anurag.electronic_store.Elecsto.repositories.ProductRepository;
import com.anurag.electronic_store.Elecsto.repositories.UserRepository;
import com.anurag.electronic_store.Elecsto.services.CartService;
import org.apache.catalina.LifecycleState;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImplementation implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        String productId = request.getProductId();
        int quantity = request.getQuantity();

        //validating quantity
        if(quantity<=0)
        {
            throw new BadApiRequestException("quantity should be greater or equal to one");
        }

        //fetching user
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id not found!"));
        //fetching product
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product with given id not found!"));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        }catch (NoSuchElementException e){
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
        }

        //perform cart addition operation
        //case1 : if item allready in the cart : then update quantity and totalPrice

        //can't do like this because we cannot change values in "map" so we've to use AtomicRefrenct class;
        //boolean itemAllreadyExist = false;
        AtomicBoolean itemAllreadyExist = new AtomicBoolean(false);

        List<CartItem> itemList = cart.getItems();
        itemList = itemList.stream().map(item -> {
            if(item.getProduct().getProductId().equals(productId))
            {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity* product.getDiscountedPrice());
                itemAllreadyExist.set(true);
            }
            return item;
        }).collect(Collectors.toList());


        //case2 : if item not exist in the cart
        if(itemAllreadyExist.get()==false){
            //create itmes
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity*product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);

        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {
        //fetching user
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("Cart with given id not found!"));
        cartItemRepository.delete(cartItem);
    }


    @Override
    public void deleteCart(String userId) {
        //fetching user
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id not found!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cart for given userId not exist!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartOfUser(String userId) {
        //fetching user
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id not found!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cart for given userId not exist!"));
        return mapper.map(cart,CartDto.class);
    }
}
