package com.yusufguc.mapper;

import com.yusufguc.dto.request.RatingRequest;
import com.yusufguc.dto.response.RatingResponse;
import com.yusufguc.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper {

    @Mapping(target = "username", source = "user.username")
    RatingResponse toResponse(Rating rating);


    void updateEntityFromRequest(RatingRequest request, @MappingTarget Rating rating);
}