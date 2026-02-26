package com.yusufguc.controller.impl;

import com.yusufguc.controller.RestaurantController;
import com.yusufguc.dto.request.RestaurantRequest;
import com.yusufguc.dto.response.RestaurantResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantControllerImpl implements RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * Creates a new restaurant profile. Authorized for 'RESTAURANT_OWNER' only.
     */
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PostMapping
    @Override
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Valid @RequestBody RestaurantRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantService.createRestaurant(request));
    }

    /**
     * Updates an existing restaurant's details. Authorized for 'RESTAURANT_OWNER' only.
     */
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/id/{restaurantId}")
    @Override
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long restaurantId,
            @Valid @RequestBody RestaurantRequest request) {

        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantId,request));
    }

    /**
     * Deletes a restaurant by its ID. Authorized for 'ADMIN' or the 'RESTAURANT_OWNER'.
     */
    @PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_OWNER')")
    @DeleteMapping("id/{restaurantId}")
    @Override
    public ResponseEntity<Object> deleteRestaurant(
            @PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a paginated list of all registered restaurants. Accessible by any authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Override
    public ResponseEntity<RestPageableResponse<RestaurantResponse>> getAllRestaurants(
            @ModelAttribute RestPageableRequest request) {

        return ResponseEntity.ok(restaurantService.getAllRestaurant(request));
    }

    /**
     * Searches for restaurants by name. Accessible by any authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    @Override
    public ResponseEntity<RestPageableResponse<RestaurantResponse>> searchByName(
            @RequestParam(name = "name", required = false) String name,
            @ModelAttribute RestPageableRequest request) {

        return ResponseEntity.ok(restaurantService.searchByName(name, request));
    }

    /**
     * Filters restaurants based on their current open/closed status. Accessible by any authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/filter")
    @Override
    public ResponseEntity<RestPageableResponse<RestaurantResponse>> filterByOpenStatus(
            @RequestParam boolean open,
            @ModelAttribute RestPageableRequest request) {

        return ResponseEntity.ok( restaurantService.filterByOpenStatus(open, request));
    }

    /**
     * Retrieves a paginated list of restaurants owned by the currently authenticated owner.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-restaurants")
    @Override
    public ResponseEntity<RestPageableResponse<RestaurantResponse>> getMyRestaurants(
            @ModelAttribute RestPageableRequest request) {

        return ResponseEntity.ok(restaurantService.getMyRestaurants(request));
    }

    /**
     * Toggles the restaurant's status between open and closed. Authorized for 'RESTAURANT_OWNER' only.
     */
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/{restaurantId}/toggle-status")
    @Override
    public ResponseEntity<RestaurantResponse> toggleOpenStatus(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.toggleOpenStatus(restaurantId));
    }

}
