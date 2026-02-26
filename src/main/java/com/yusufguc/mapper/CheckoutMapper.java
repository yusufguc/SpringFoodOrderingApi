package com.yusufguc.mapper;

import com.yusufguc.dto.request.OrderItemRequest;
import com.yusufguc.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    OrderItemRequest cartItemToOrderItem(CartItem cartItem);

    List<OrderItemRequest> cartItemsToOrderItems(List<CartItem> items);
}