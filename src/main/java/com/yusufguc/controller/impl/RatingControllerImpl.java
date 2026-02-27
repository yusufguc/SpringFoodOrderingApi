package com.yusufguc.controller.impl;

import com.yusufguc.controller.RatingController;
import com.yusufguc.dto.request.RatingRequest;
import com.yusufguc.dto.response.RatingResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingControllerImpl implements RatingController {

    private final RatingService ratingService;

    /**
     * Submits a new rating or updates an existing one for a restaurant.
     * Restricted to users with 'USER' role to ensure genuine customer feedback.
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<Void> rateRestaurant(@Valid @RequestBody RatingRequest request) {
        ratingService.rateRestaurant(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves all ratings for a specific restaurant with pagination support.
     * Open to all authenticated users (or public, depending on your security config).
     */
    @GetMapping("/restaurant/{restaurantId}")
    @Override
    public ResponseEntity<RestPageableResponse<RatingResponse>> getRestaurantRatings(
            @PathVariable Long restaurantId,
            @Valid RestPageableRequest request) {
        return ResponseEntity.ok(ratingService.getRestaurantRatings(restaurantId, request));
    }

    /**
     * Deletes a specific rating by its ID.
     * Security check within the service ensures only the owner or an admin can delete.
     */
    @DeleteMapping("/{ratingId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }
}
