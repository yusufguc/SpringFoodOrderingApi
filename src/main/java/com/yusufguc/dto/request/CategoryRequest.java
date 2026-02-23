package com.yusufguc.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

}
