package com.yusufguc.controller.impl;

import com.yusufguc.controller.CategoryController;
import com.yusufguc.dto.request.CategoryRequest;
import com.yusufguc.dto.response.CategoryResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PostMapping("/restaurants/{restaurantId}/categories")
    @Override
    public ResponseEntity<CategoryResponse> createCategory(@PathVariable Long restaurantId,
                                                           @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(restaurantId,request));
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/categories/{categoryId}")
    @Override
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId,
                                                           @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId,request));
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @DeleteMapping("/categories/{categoryId}")
    @Override
    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/categories/{categoryId}")
    @Override
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/restaurants/{restaurantId}/categories")
    @Override
    public ResponseEntity<RestPageableResponse<CategoryResponse>> getCategoriesByRestaurant(@PathVariable Long restaurantId,
                                                                                            @ModelAttribute RestPageableRequest request) {
        return ResponseEntity.ok(categoryService.getCategoriesByRestaurant(restaurantId,request));
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @GetMapping("/restaurants/{restaurantId}/my-categories")
    @Override
    public ResponseEntity<RestPageableResponse<CategoryResponse>> getMyCategories(@PathVariable Long restaurantId,
                                                                                  @ModelAttribute RestPageableRequest request) {

        return ResponseEntity.ok(categoryService.getMyCategories(restaurantId,request));
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @GetMapping("/restaurants/{restaurantId}/categories/search")
    @Override
    public ResponseEntity<RestPageableResponse<CategoryResponse>> searchByName(@PathVariable Long restaurantId,
                                                                               @RequestParam String name,
                                                                               @ModelAttribute RestPageableRequest request) {
        return ResponseEntity.ok(categoryService.searchByName(restaurantId,name,request));
    }
}
