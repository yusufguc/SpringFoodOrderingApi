package com.yusufguc.service;

import com.yusufguc.dto.request.RestaurantRequest;
import com.yusufguc.dto.response.RestaurantResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;

public interface RestaurantService {

    public RestaurantResponse createRestaurant(RestaurantRequest request);

    public RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request);

    public void deleteRestaurant(Long restaurantId);

    public RestPageableResponse<RestaurantResponse> getAllRestaurant(RestPageableRequest request);

    public RestPageableResponse<RestaurantResponse> searchByName(String name, RestPageableRequest request);

    public RestPageableResponse<RestaurantResponse> filterByOpenStatus(boolean open, RestPageableRequest request);

    public  RestPageableResponse<RestaurantResponse> getMyRestaurants(RestPageableRequest request);

}

