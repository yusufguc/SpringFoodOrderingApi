package com.yusufguc.repository;

import com.yusufguc.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUserIdAndRestaurantId(Long userId, Long restaurantId);

    /**
     * Calculates the average score for a specific restaurant.
     */
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.restaurant.id = :restaurantId")
    Double getAverageRatingByRestaurantId(@Param("restaurantId") Long restaurantId);

    Page<Rating> findAllByRestaurantId(Long restaurantId, Pageable pageable);
}