package com.yusufguc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing the public details of a product.
 * Provides a clean view of menu items for customers and owners.
 */
@Getter
@AllArgsConstructor
public class ProductResponse {

    private  Long id;

    private  String name;

    private String description;

    private BigDecimal price;

    private  Integer stock;

    private  boolean active;
}
