package com.yusufguc.controller;

import com.yusufguc.dto.request.CartItemRequest;
import com.yusufguc.dto.response.CartResponse;
import com.yusufguc.dto.response.OrderResponse;
import org.springframework.http.ResponseEntity;


public interface CartController {

    public ResponseEntity<CartResponse> addToCart( CartItemRequest request);

    public ResponseEntity<CartResponse> getMyCart();

    public ResponseEntity<CartResponse> removeFromCart(Long productId,Integer quantity);

    public ResponseEntity<Void> clearCart();

    public ResponseEntity<OrderResponse> checkout();
}
