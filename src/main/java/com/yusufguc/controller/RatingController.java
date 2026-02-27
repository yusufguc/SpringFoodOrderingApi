package com.yusufguc.controller;

import com.yusufguc.dto.request.RatingRequest;
import com.yusufguc.dto.response.RatingResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import org.springframework.http.ResponseEntity;

public interface RatingController {

    public ResponseEntity<Void> rateRestaurant(RatingRequest request);

    public ResponseEntity<RestPageableResponse<RatingResponse>> getRestaurantRatings(
             Long restaurantId,
             RestPageableRequest request);

    public ResponseEntity<Void> deleteRating(Long ratingId);

}
