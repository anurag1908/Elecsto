package com.anurag.electronic_store.Elecsto.dtos;

import com.anurag.electronic_store.Elecsto.entities.Order;
import com.anurag.electronic_store.Elecsto.entities.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;
}
