package com.yusufguc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents an individual item within a specific customer order.
 * Captures a snapshot of the product details at the time of purchase.
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The parent order this item belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * The specific product associated with this order item.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * The number of units purchased for this specific product.
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * The unit price of the product at the moment the order was placed.
     * Stored separately to preserve historical price data even if product prices change.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
