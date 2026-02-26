package com.yusufguc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object providing a public overview of a restaurant.
 * Includes both operational details and simplified owner information.
 */
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
