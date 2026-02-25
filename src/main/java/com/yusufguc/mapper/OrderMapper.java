package com.yusufguc.mapper;

import com.yusufguc.dto.request.OrderItemRequest;
import com.yusufguc.dto.request.OrderRequest;
import com.yusufguc.dto.response.OrderItemResponse;
import com.yusufguc.dto.response.OrderResponse;
import com.yusufguc.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderItem toEntity(OrderItemRequest request, Product product);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "total", expression = "java(item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))")
    OrderItemResponse toResponse(OrderItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderRequest request, User user, Restaurant restaurant);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "items", source = "items") 
    OrderResponse toResponse(Order order);
}