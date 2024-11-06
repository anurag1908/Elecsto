package com.anurag.electronic_store.Elecsto.repositories;

import com.anurag.electronic_store.Elecsto.entities.Cart;
import com.anurag.electronic_store.Elecsto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {
     Optional<Cart> findByUser(User user);
}
