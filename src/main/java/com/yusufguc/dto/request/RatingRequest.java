package com.yusufguc.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for submitting a restaurant rating.
 * Includes validation to ensure the score is within the 1-5 star range.
 */
@Getter
@Setter
public class RatingRequest {


    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;


    @NotNull(message = "Score is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer score;


    private String comment;
}