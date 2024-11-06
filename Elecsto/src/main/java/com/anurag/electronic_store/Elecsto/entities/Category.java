package com.anurag.electronic_store.Elecsto.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id")
    private String categoryId;

    @Column(name = "category_title",length = 50,nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    private String coverImage;

    //FetchType.LAZY : to not fetch products when we fetch category , they will be fetched only when asked to fetch
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> productsList = new ArrayList<>();
}