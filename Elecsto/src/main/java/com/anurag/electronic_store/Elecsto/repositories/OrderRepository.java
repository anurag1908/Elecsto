package com.anurag.electronic_store.Elecsto.repositories;

import com.anurag.electronic_store.Elecsto.entities.Order;
import com.anurag.electronic_store.Elecsto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findByUser(User user);
}
