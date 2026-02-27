package com.yusufguc.service.impl;

import com.yusufguc.dto.request.RatingRequest;
import com.yusufguc.dto.response.RatingResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.mapper.RatingMapper;
import com.yusufguc.model.Rating;
import com.yusufguc.model.Restaurant;
import com.yusufguc.model.User;
import com.yusufguc.pagination.PagerUtil;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.repository.RatingRepository;
import com.yusufguc.repository.RestaurantRepository;
import com.yusufguc.security.CurrentUserService;
import com.yusufguc.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;
    private final CurrentUserService currentUserService;
    private final RatingMapper ratingMapper;


    /**
     * Handles the creation or update of a restaurant rating by the current user.
     * Maps the request data to the entity and triggers a recalculation of the restaurant's average rating.
     */
    @Transactional
    @Override
    public void rateRestaurant(RatingRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, request.getRestaurantId().toString())));

        Rating rating = ratingRepository.findByUserIdAndRestaurantId(currentUser.getId(), restaurant.getId())
                .orElseGet(() -> Rating.builder()
                        .user(currentUser)
                        .restaurant(restaurant)
                        .build());

        ratingMapper.updateEntityFromRequest(request, rating);

        ratingRepository.save(rating);

        updateRestaurantAverageRating(restaurant);
    }

    /**
     * Recalculates and synchronizes the average rating of a restaurant.
     * If no ratings exist, the rating is reset to 0.
     */
    private void updateRestaurantAverageRating(Restaurant restaurant) {
        Double average = ratingRepository.getAverageRatingByRestaurantId(restaurant.getId());

        if (average != null) {
            restaurant.setRating((int) Math.round(average));
        } else {
            restaurant.setRating(0);
        }
        restaurantRepository.save(restaurant);
    }

    /**
     * Fetches a paginated list of ratings for a specific restaurant.
     */
    @Transactional(readOnly = true)
    @Override
    public RestPageableResponse<RatingResponse> getRestaurantRatings(Long restaurantId, RestPageableRequest request) {
        Pageable pageable = PagerUtil.toPageable(request);

        Page<Rating> ratingPage = ratingRepository.findAllByRestaurantId(restaurantId, pageable);

        return PagerUtil.toPageResponse(ratingPage, ratingMapper::toResponse);
    }

    /**
     * Deletes a specific rating and updates the restaurant's overall rating score.
     * Flushes the persistence context before recalculation to ensure the deleted rating is not included in the new average.
     */
    @Transactional
    @Override
    public void deleteRating(Long ratingId) {
        User currentUser = currentUserService.getCurrentUser();
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RATING_NOT_FOUND, ratingId.toString())));

        if (!rating.getUser().getId().equals(currentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_RATING_OWNER, currentUser.getId().toString()));
        }

        Long restaurantId = rating.getRestaurant().getId();

        ratingRepository.delete(rating);
        ratingRepository.flush();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        updateRestaurantAverageRating(restaurant);
    }
}