package com.yusufguc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents the shopping cart for a user.
 * Each user has one unique cart associated with a single restaurant at a time.
 */
@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The owner of the cart.
     * Each user can have only one active cart (One-to-One relationship).
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * The restaurant from which the items are being added.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    /**
     * List of items currently held in the cart.
     * Managed with CascadeType.ALL to handle automatic updates and deletions.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    /**
     * The total price of all items in the cart.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
}

