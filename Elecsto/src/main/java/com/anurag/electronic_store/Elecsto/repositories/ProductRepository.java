package com.anurag.electronic_store.Elecsto.repositories;

import com.anurag.electronic_store.Elecsto.entities.Category;
import com.anurag.electronic_store.Elecsto.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findByTitleContaining(String subStirng);
    List<Product> findByInStockTrue();
    List<Product> findByCategory(Category category);
}
