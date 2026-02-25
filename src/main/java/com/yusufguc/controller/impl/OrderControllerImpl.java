package com.yusufguc.controller.impl;

import com.yusufguc.controller.OrderController;
import com.yusufguc.dto.request.OrderRequest;
import com.yusufguc.dto.response.OrderResponse;
import com.yusufguc.model.enums.OrderStatus;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest orderRequest) {
        OrderResponse response = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/{orderId}/status")
    @Override
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId,
                                                           @RequestParam OrderStatus newStatus) {
        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, newStatus));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{orderId}/cancel")
    @Override
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Override
    public ResponseEntity<RestPageableResponse<OrderResponse>> getMyOrders(
            @ModelAttribute RestPageableRequest pageableRequest) {
        return ResponseEntity.ok(orderService.getMyOrders(pageableRequest));
    }

    @PreAuthorize("hasAnyRole('USER','RESTAURANT_OWNER')")
    @GetMapping("/{orderId}")
    @Override
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @GetMapping("/restaurant/{restaurantId}")
    @Override
    public ResponseEntity<RestPageableResponse<OrderResponse>> getOrdersByRestaurant(@PathVariable Long restaurantId,
                                                                                     @ModelAttribute RestPageableRequest pageableRequest) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restaurantId,pageableRequest));
    }

}
