package com.anurag.electronic_store.Elecsto.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDto {
    private String orderId;
    private String orderStatus = "PENDING";
    private String paymentStatus = "NOT PAID";
    private int orderAmount;
    private String billingAddress;
    private String billingContact;
    private String billingName;
    private Date orderedDate = new Date();
    private Date deliveredDate;
    //private UserDto user;
    private List<OrderItemDto> orderItemsList = new ArrayList<>();
}
