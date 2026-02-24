package com.yusufguc.mapper;

import com.yusufguc.dto.request.ProductRequest;
import com.yusufguc.dto.response.ProductResponse;
import com.yusufguc.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "active", ignore = true)
    Product toEntity(ProductRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateProductFromRequest(ProductRequest request, @MappingTarget Product product);
}