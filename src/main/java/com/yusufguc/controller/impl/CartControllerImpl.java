package com.yusufguc.controller.impl;

import com.yusufguc.controller.CartController;
import com.yusufguc.dto.request.CartItemRequest;
import com.yusufguc.dto.response.CartResponse;
import com.yusufguc.dto.response.OrderResponse;
import com.yusufguc.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    @Override
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-cart")
    @Override
    public ResponseEntity<CartResponse> getMyCart() {
        return ResponseEntity.ok(cartService.getMyCart());
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/remove/{productId}")
    @Override
    public ResponseEntity<CartResponse> removeFromCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        return ResponseEntity.ok(cartService.removeFromCart(productId, quantity));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/clear")
    @Override
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/checkout")
    @Override
    public ResponseEntity<OrderResponse> checkout() {
        return ResponseEntity.ok(cartService.checkout());
    }
}