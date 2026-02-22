package com.yusufguc.repository;

import com.yusufguc.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    Page<Restaurant> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Restaurant>  findByOpen(boolean open,Pageable pageable);
    Page<Restaurant> findByOwnerId(Long ownerId, Pageable pageable);
}
