package com.yusufguc.model;

import com.yusufguc.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer order in the system.
 * Manages the relationship between a user, a restaurant, and the purchased items.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The customer who placed the order.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private  User user;

    /**
     * The restaurant where the order was placed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private  Restaurant restaurant;

    /**
     * List of individual items included in this order.
     * Managed with CascadeType.ALL to ensure items are handled alongside the order.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    /**
     * The total calculated cost of the order.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice=BigDecimal.ZERO;

    /**
     * Current status of the order (e.g., PENDING, CONFIRMED, DELIVERED).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Calculates the total price of the order based on item prices and quantities.
     */
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Initializes default order status before persisting to the database.
     */
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }

}
