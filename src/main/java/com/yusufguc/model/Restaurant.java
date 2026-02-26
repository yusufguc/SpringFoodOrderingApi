package com.yusufguc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a restaurant entity in the system.
 * Stores restaurant details and their association with an owner.
 */
@Entity
@Table(name = "restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String address;

    /**
     * The owner of the restaurant.
     * Linked to the User entity with a many-to-one relationship.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    /**
     * Indicates whether the restaurant is currently accepting orders.
     */
    @Column(nullable = false)
    private  boolean open;

    /**
     * The average rating of the restaurant, constrained between 1 and 5 stars.
     */
    @Min(1)
    @Max(5)
    private Integer rating;
}
