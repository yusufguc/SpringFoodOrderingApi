package com.yusufguc.controller;

import com.yusufguc.dto.request.CategoryRequest;
import com.yusufguc.dto.response.CategoryResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryController {
    public ResponseEntity<CategoryResponse> createCategory(Long restaurantId, CategoryRequest request);

    public ResponseEntity<CategoryResponse> updateCategory(Long categoryId, CategoryRequest request);

    public ResponseEntity<Object> deleteCategory(Long categoryId);

    public ResponseEntity<CategoryResponse> getCategoryById(Long categoryId);

    public ResponseEntity<RestPageableResponse<CategoryResponse>> getCategoriesByRestaurant(Long restaurantId, RestPageableRequest request);

    public ResponseEntity<RestPageableResponse<CategoryResponse>> getMyCategories(Long restaurantId, RestPageableRequest request);

    public ResponseEntity<RestPageableResponse<CategoryResponse>> searchByName(Long restaurantId, String name, RestPageableRequest request);
}
