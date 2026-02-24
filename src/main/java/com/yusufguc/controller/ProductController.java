package com.yusufguc.controller;

import com.yusufguc.dto.request.ProductRequest;
import com.yusufguc.dto.request.StockUpdateRequest;
import com.yusufguc.dto.response.ProductResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import org.springframework.http.ResponseEntity;

public interface ProductController {

    public ResponseEntity<ProductResponse> createProduct(Long categoryId, ProductRequest productRequest);
    public ResponseEntity<ProductResponse> updateProduct(Long productId, ProductRequest productRequest);
    public ResponseEntity<Object> deleteProduct(Long productId);
    public ResponseEntity<ProductResponse> getProductById(Long productId);
    public ResponseEntity<ProductResponse> toggleProductStatus(Long productId);
    public ResponseEntity<ProductResponse> updateStock(Long productId, StockUpdateRequest request);
    public ResponseEntity<RestPageableResponse<ProductResponse>> getProductsByCategory(Long categoryId, RestPageableRequest pageableRequest);
}
