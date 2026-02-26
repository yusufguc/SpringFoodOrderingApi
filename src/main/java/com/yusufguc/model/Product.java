package com.yusufguc.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents a menu item or product within a restaurant.
 * Managed by the restaurant owner and belongs to a specific category.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private  String name;

    @Column(length = 250)
    private String description;

    /**
     * The unit price of the product.
     * Uses BigDecimal for high financial precision (e.g., 99.99).
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Current available quantity of the product in inventory.
     */
    @Column(nullable = false)
    private  Integer stock;

    /**
     * The category this product belongs to (e.g., Beverages, Desserts).
     * Linked with a many-to-one relationship to the Category entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Indicates whether the product is currently visible and available for customers.
     */
    @Column(nullable = false)
    private  boolean active;
}
