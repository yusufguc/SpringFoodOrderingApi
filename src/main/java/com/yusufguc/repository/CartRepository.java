package com.yusufguc.repository;

import com.yusufguc.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(Long id);

    void deleteByUserId(Long userId);
}
