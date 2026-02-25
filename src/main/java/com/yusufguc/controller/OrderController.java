package com.yusufguc.controller;

import com.yusufguc.dto.request.OrderRequest;
import com.yusufguc.dto.response.OrderResponse;
import com.yusufguc.model.enums.OrderStatus;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import org.springframework.http.ResponseEntity;

public interface OrderController {

    public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest);

    public ResponseEntity<OrderResponse> updateOrderStatus(Long orderId, OrderStatus newStatus);

    public ResponseEntity<OrderResponse> cancelOrder(Long orderId);

    public ResponseEntity<RestPageableResponse<OrderResponse>> getMyOrders(RestPageableRequest pageableRequest);

    public ResponseEntity<OrderResponse> getOrderById(Long orderId);

    public ResponseEntity<RestPageableResponse<OrderResponse>> getOrdersByRestaurant(Long restaurantID, RestPageableRequest pageableRequest);
}
