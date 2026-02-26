package com.yusufguc.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Data Transfer Object representing a menu category.
 * Used to return simplified category details to the client.
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private Long restaurantId;
}
