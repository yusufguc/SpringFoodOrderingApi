package com.yusufguc.service;

import com.yusufguc.dto.request.CartItemRequest;
import com.yusufguc.dto.response.CartResponse;
import com.yusufguc.dto.response.OrderResponse;

public interface CartService {

    CartResponse addToCart(CartItemRequest request);

    CartResponse removeFromCart(Long productId, Integer quantity);

    CartResponse getMyCart();

    void clearCart();

    OrderResponse checkout();
}