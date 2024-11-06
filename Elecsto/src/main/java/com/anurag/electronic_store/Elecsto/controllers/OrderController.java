package com.anurag.electronic_store.Elecsto.controllers;


import com.anurag.electronic_store.Elecsto.dtos.*;
import com.anurag.electronic_store.Elecsto.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create order
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        OrderDto order = orderService.createOrder(createOrderRequest);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //remove order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Order is removed !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //get all orders of a particular user
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrderOfUser(@PathVariable String userId){
        List<OrderDto> orders = orderService.getAllOrderOfUser(userId);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderDto> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

}