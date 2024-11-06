package com.anurag.electronic_store.Elecsto.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {


    @NotBlank(message = "Cart id is required!")
    private String cartId;
    @NotBlank(message = "user id is required!")
    private String userId;

    private String orderStatus = "PENDING";
    private String paymentStatus = "NOT PAID";

    @NotBlank(message = "Address is required!")
    private String billingAddress;
    @NotBlank(message = "Contact details is required!")
    private String billingContact;
    @NotBlank(message = "Billing Name is required!")
    private String billingName;
}
