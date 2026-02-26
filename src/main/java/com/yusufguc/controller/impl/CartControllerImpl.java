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

    /**
     * Adds a new product to the authenticated user's cart or updates the quantity of an existing item.
     * Restricted to users with the 'USER' role.
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    @Override
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    /**
     * Retrieves the current shopping cart details, including items and total price, for the authenticated user.
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-cart")
    @Override
    public ResponseEntity<CartResponse> getMyCart() {
        return ResponseEntity.ok(cartService.getMyCart());
    }

    /**
     * Decreases the quantity of a specific product in the cart.
     * If the quantity reaches zero or no quantity is specified, the item is removed from the cart.
     */
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/remove/{productId}")
    @Override
    public ResponseEntity<CartResponse> removeFromCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        return ResponseEntity.ok(cartService.removeFromCart(productId, quantity));
    }

    /**
     * Removes all items from the user's shopping cart.
     * Returns '204 No Content' upon successful operation.
     */
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/clear")
    @Override
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
    /**
     * Validates and converts the current cart items into a formal Order.
     * Clears the cart upon successful order creation and returns the order summary.
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/checkout")
    @Override
    public ResponseEntity<OrderResponse> checkout() {
        return ResponseEntity.ok(cartService.checkout());
    }
}