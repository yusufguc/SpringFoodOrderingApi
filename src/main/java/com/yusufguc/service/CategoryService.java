package com.yusufguc.service;


import com.yusufguc.dto.request.CategoryRequest;
import com.yusufguc.dto.response.CategoryResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;

public interface CategoryService {
    public CategoryResponse createCategory(Long restaurantId, CategoryRequest request);

    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request);

    public void deleteCategory(Long categoryId);

    public CategoryResponse getCategoryById(Long categoryId);

    public RestPageableResponse<CategoryResponse> getCategoriesByRestaurant(
            Long restaurantId,
            RestPageableRequest request
            );

    public RestPageableResponse<CategoryResponse> getMyCategories(
            Long restaurantId,
            RestPageableRequest request
    );

    public RestPageableResponse<CategoryResponse> searchByName(
            Long restaurantId,
            String name,
            RestPageableRequest request
    );

}







