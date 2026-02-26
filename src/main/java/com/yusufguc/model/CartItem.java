package com.yusufguc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents an individual item within a user's shopping cart.
 * Links specific products to a cart with quantity and price details.
 */
@Entity
@Table(name = "cart_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The parent shopping cart this item belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    /**
     * The specific product added to the cart.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private  Product product;

    /**
     * The number of units of the product in the cart.
     */
    @Column(nullable = false)
    private  Integer quantity;

    /**
     * The unit price of the product at the time it was added to the cart.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
