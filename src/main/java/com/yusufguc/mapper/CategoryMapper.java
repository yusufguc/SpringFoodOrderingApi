package com.yusufguc.mapper;

import com.yusufguc.dto.request.CategoryRequest;
import com.yusufguc.dto.response.CategoryResponse;
import com.yusufguc.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "name", expression = "java(request.getName().trim())")
    Category toEntity(CategoryRequest request);

    @Mapping(source = "restaurant.id", target = "restaurantId")
    CategoryResponse toResponse(Category category);

    @Mapping(target = "restaurant", ignore = true)
    void updateEntityFromRequest(CategoryRequest request, @MappingTarget Category category);
}