package com.yusufguc.service;

import com.yusufguc.dto.request.OrderRequest;
import com.yusufguc.dto.response.OrderResponse;
import com.yusufguc.model.enums.OrderStatus;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;

public interface OrderService {

    public OrderResponse createOrder(OrderRequest orderRequest);

    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus);

    public OrderResponse cancelOrder(Long orderId);

    public RestPageableResponse<OrderResponse> getMyOrders(RestPageableRequest pageableRequest);

    public  OrderResponse getOrderById(Long orderId);

    public RestPageableResponse<OrderResponse> getOrdersByRestaurant(Long restaurantID,RestPageableRequest pageableRequest);
}


