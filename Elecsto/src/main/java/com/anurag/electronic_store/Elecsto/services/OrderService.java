package com.anurag.electronic_store.Elecsto.services;

import com.anurag.electronic_store.Elecsto.dtos.CreateOrderRequest;
import com.anurag.electronic_store.Elecsto.dtos.OrderDto;

import java.util.List;

public interface OrderService {
    //create order
    OrderDto createOrder(CreateOrderRequest orderRequest);
    //remove order
    void removeOrder(String orderId);
    //get all orders of user
    List<OrderDto> getAllOrderOfUser(String userId);
    //get orders (for admin)
    List<OrderDto> getAllOrders();
}
