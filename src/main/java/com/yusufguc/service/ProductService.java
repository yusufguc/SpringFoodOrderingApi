package com.yusufguc.service;

import com.yusufguc.dto.request.ProductRequest;
import com.yusufguc.dto.request.StockUpdateRequest;
import com.yusufguc.dto.response.ProductResponse;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;

public interface ProductService {

    public ProductResponse createProduct(Long categoryId,ProductRequest productRequest);

    public ProductResponse updateProduct(Long productId,ProductRequest productRequest);

    public  void  deleteProduct(Long productId);

    public  ProductResponse getProductById(Long productId);

    public  ProductResponse toggleProductStatus(Long productId);

    public  ProductResponse updateStock(Long productId,StockUpdateRequest request);

    public RestPageableResponse<ProductResponse> getProductsByCategory(Long categoryId, RestPageableRequest pageableRequest);
}

