package com.anurag.electronic_store.Elecsto.services.implementation;

import com.anurag.electronic_store.Elecsto.dtos.CreateOrderRequest;
import com.anurag.electronic_store.Elecsto.dtos.OrderDto;
import com.anurag.electronic_store.Elecsto.dtos.OrderItemDto;
import com.anurag.electronic_store.Elecsto.entities.*;
import com.anurag.electronic_store.Elecsto.exceptions.BadApiRequestException;
import com.anurag.electronic_store.Elecsto.exceptions.ResourceNotFoundException;
import com.anurag.electronic_store.Elecsto.repositories.CartRepository;
import com.anurag.electronic_store.Elecsto.repositories.OrderRepository;
import com.anurag.electronic_store.Elecsto.repositories.UserRepository;
import com.anurag.electronic_store.Elecsto.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {

        String userId = createOrderRequest.getUserId();
        String cartId = createOrderRequest.getCartId();

        //Fetching User and its corresponding Cart
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with given ID!"));
        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart not found with given cart ID!"));

        List<CartItem> cartItems = cart.getItems();

        if(cartItems.size() <= 0){
            throw new BadApiRequestException("Items in the cart should be more than or equal to 1!");
        }

        Order order = Order.builder()
                .billingAddress(createOrderRequest.getBillingAddress())
                .billingContact(createOrderRequest.getBillingContact())
                .billingName(createOrderRequest.getBillingName())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(createOrderRequest.getPaymentStatus())
                .orderStatus(createOrderRequest.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicInteger orderTotalAmount = new AtomicInteger(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem->{
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity()* cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
            orderTotalAmount.set(orderTotalAmount.addAndGet(orderItem.getTotalPrice()));
            return orderItem;
        }).collect(Collectors.toList());


        order.setOrderItemList(orderItems);
        order.setOrderAmount(orderTotalAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order not exist!"));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getAllOrderOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User does not exist!"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> ordersDtoList = orders.stream().map(order->{
            return mapper.map(order,OrderDto.class);
        }).collect(Collectors.toList());

        return ordersDtoList;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> allOrders = orderRepository.findAll();
        List<OrderDto> allOrderDto = allOrders.stream().map(order->{
            return mapper.map(order,OrderDto.class);
        }).collect(Collectors.toList());

        return allOrderDto;
    }
}
