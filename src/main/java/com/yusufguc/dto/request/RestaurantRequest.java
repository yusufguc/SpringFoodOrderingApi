package com.yusufguc.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Size(max = 250)
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    private  boolean open;

}
