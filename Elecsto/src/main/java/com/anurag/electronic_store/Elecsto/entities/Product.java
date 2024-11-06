package com.anurag.electronic_store.Elecsto.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    private String productId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 4000,nullable = false)
    private String description;

    @Column(nullable = false )
    private int price;

    @Column(nullable = false)
    private int discountedPrice;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Date dateAdded;

    @Column(nullable = false)
    private boolean inStock;

    private String imageName;

    //FetchType.EAGER : to get category when a product is accesed
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}
