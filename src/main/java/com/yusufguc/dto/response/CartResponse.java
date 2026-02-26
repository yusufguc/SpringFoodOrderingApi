package com.yusufguc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object for the complete shopping cart details.
 * Aggregates all items and provides the final calculated total for the user.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private List<CartItemResponse> items;
    private BigDecimal totalPrice;
}