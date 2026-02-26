package com.yusufguc.controller.impl;

import com.yusufguc.controller.ProductController;
import com.yusufguc.dto.request.ProductRequest;
import com.yusufguc.dto.request.StockUpdateRequest;
import com.yusufguc.dto.response.ProductResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    /**
     * Creates a new product under a specific category. Authorized for 'RESTAURANT_OWNER' only.
     */
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PostMapping("/categories/{categoryId}/products")
    @Override
    public ResponseEntity<ProductResponse> createProduct(
            @PathVariable Long categoryId,
            @Valid @RequestBody ProductRequest productRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(categoryId,productRequest));
    }

    /**
     * Updates an existing product's details. Authorized for 'RESTAURANT_OWNER' only.
     */
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/products/{productId}")
    @Override
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId,
                                                         @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(productId,productRequest));
    }

    /**
     * Deletes a product by its ID. Authorized for 'RESTAURANT_OWNER'. Returns '204 No Content' on success.
     */
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @DeleteMapping("/products/{productId}")
    @Override
    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves specific product details by ID. Accessible by any authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/products/{productId}")
    @Override
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    /**
     * Toggles the active/passive status of a product. Authorized for 'RESTAURANT_OWNER' only.
     */
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/products/{productId}/toggle-status")
    @Override
    public ResponseEntity<ProductResponse> toggleProductStatus(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.toggleProductStatus(productId));
    }

    /**
     * Updates the stock quantity of a product. Authorized for 'RESTAURANT_OWNER' only.
     */
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/products/{productId}/update-stock")
    @Override
    public ResponseEntity<ProductResponse> updateStock(@PathVariable Long productId,
                                                       @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(productService.updateStock(productId,request));
    }

    /**
     * Retrieves a paginated list of products belonging to a specific category. Accessible by any authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/category/{categoryId}/products")
    @Override
    public ResponseEntity<RestPageableResponse<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId,
                                                                                       @ModelAttribute RestPageableRequest pageableRequest) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId,pageableRequest));
    }
}
