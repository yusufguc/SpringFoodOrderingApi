package com.yusufguc.mapper;

import com.yusufguc.dto.request.CartItemRequest;
import com.yusufguc.dto.request.CartRequest;
import com.yusufguc.dto.response.CartItemResponse;
import com.yusufguc.dto.response.CartResponse;
import com.yusufguc.model.Cart;
import com.yusufguc.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    // ---------------------
    // Cart → CartResponse
    // ---------------------
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "items", target = "items")
    CartResponse toCartResponse(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    CartItemResponse toCartItemResponse(CartItem item);

    List<CartItemResponse> toCartItemResponseList(List<CartItem> items);

    // ---------------------
    // CartRequest → Cart (entity)
    // ---------------------
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "restaurantId", target = "restaurant.id")
    @Mapping(source = "items", target = "items")
    @Mapping(target = "totalPrice", ignore = true)
    Cart toEntity(CartRequest request);


    // ---------------------
    // CartItemRequest → CartItem (entity)
    // ---------------------
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(source = "productId", target = "product.id")
    @Mapping(target = "price", ignore = true)
    CartItem toEntity(CartItemRequest request);

}