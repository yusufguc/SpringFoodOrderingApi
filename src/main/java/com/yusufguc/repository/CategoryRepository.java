package com.yusufguc.repository;

import com.yusufguc.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByNameIgnoreCaseAndRestaurant_Id(String name, Long restaurantId) ;

    boolean existsByNameIgnoreCaseAndRestaurant_IdAndIdNot(
            String name,
            Long restaurantId,
            Long id
    );

    Page<Category> findByRestaurantId(Long restaurantId, Pageable pageable);

    Page<Category> findByRestaurantIdAndNameContainingIgnoreCase(Long restaurantId,
                                                                 String name,
                                                                 Pageable pageable);
}
