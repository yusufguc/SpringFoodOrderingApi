package com.yusufguc.mapper;

import com.yusufguc.dto.response.OwnerRequestResponse;
import com.yusufguc.model.OwnerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OwnerRequestMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    OwnerRequestResponse toResponse(OwnerRequest ownerRequest);
}