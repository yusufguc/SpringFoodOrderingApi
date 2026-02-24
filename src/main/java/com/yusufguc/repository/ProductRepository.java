package com.yusufguc.repository;

import com.yusufguc.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByCategoryIdAndNameIgnoreCase(Long categoryId, String name);

    Page<Product> findByCategoryIdAndActiveTrue(
            Long categoryId,
            Pageable pageable
    );
}
