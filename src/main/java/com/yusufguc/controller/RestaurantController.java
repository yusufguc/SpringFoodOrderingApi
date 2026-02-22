package com.yusufguc.controller;

import com.yusufguc.dto.request.RestaurantRequest;
import com.yusufguc.dto.response.RestaurantResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import org.springframework.http.ResponseEntity;

public interface RestaurantController {

    public ResponseEntity<RestaurantResponse> createRestaurant(RestaurantRequest request);

    public ResponseEntity<RestaurantResponse> updateRestaurant(Long restaurantId, RestaurantRequest request);

    public ResponseEntity<Object> deleteRestaurant(Long restaurantId);

    public ResponseEntity<RestPageableResponse<RestaurantResponse>> getAllRestaurants(RestPageableRequest request);

    public ResponseEntity<RestPageableResponse<RestaurantResponse>> searchByName(String name, RestPageableRequest request);

    public ResponseEntity<RestPageableResponse<RestaurantResponse>> filterByOpenStatus(boolean open, RestPageableRequest request);

    public ResponseEntity<RestPageableResponse<RestaurantResponse>> getMyRestaurants(RestPageableRequest request);
}
