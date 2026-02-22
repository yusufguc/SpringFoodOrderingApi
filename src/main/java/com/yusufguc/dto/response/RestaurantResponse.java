package com.yusufguc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {

    private Long id;

    private String name;

    private String description;

    private Long ownerId;

    private String ownerUsername;

    private String address;

    private  boolean open;

    private Integer rating;
}
