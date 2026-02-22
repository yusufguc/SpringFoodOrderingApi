package com.yusufguc.mapper;


import com.yusufguc.dto.request.RestaurantRequest;
import com.yusufguc.dto.response.RestaurantResponse;
import com.yusufguc.model.Restaurant;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    Restaurant toEntity(RestaurantRequest request);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.username", target = "ownerUsername")
    RestaurantResponse toResponse(Restaurant restaurant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRestaurantFromDto(RestaurantRequest request, @MappingTarget Restaurant restaurant);


}
