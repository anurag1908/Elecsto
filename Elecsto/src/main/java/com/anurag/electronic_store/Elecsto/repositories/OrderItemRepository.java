package com.anurag.electronic_store.Elecsto.repositories;

import com.anurag.electronic_store.Elecsto.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {

}
