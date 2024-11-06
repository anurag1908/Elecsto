package com.anurag.electronic_store.Elecsto.repositories;

import com.anurag.electronic_store.Elecsto.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
}
