package com.yusufguc.service;

import com.yusufguc.dto.request.RatingRequest;
import com.yusufguc.dto.response.RatingResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;

public interface RatingService {

    public void rateRestaurant(RatingRequest request);

    public RestPageableResponse<RatingResponse> getRestaurantRatings(Long restaurantId, RestPageableRequest request);

    public void deleteRating(Long ratingId);

}
