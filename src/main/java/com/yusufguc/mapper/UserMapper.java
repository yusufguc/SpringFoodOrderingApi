package com.yusufguc.mapper;

import com.yusufguc.dto.request.RegisterRequest;
import com.yusufguc.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", constant = "USER")
    User toUser(RegisterRequest request);
}
