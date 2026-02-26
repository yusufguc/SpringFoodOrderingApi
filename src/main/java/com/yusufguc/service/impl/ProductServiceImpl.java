package com.yusufguc.service.impl;

import com.yusufguc.dto.request.ProductRequest;
import com.yusufguc.dto.request.StockUpdateRequest;
import com.yusufguc.dto.response.ProductResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.mapper.ProductMapper;
import com.yusufguc.model.Category;
import com.yusufguc.model.Product;
import com.yusufguc.model.User;
import com.yusufguc.pagination.PagerUtil;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.repository.CategoryRepository;
import com.yusufguc.repository.ProductRepository;
import com.yusufguc.security.CurrentUserService;
import com.yusufguc.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final CurrentUserService currentUserService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    /**
     * Creates a new product or updates stock if product with the same name exists in the category.
     * Only the restaurant owner can create or update products.
     */
    @Transactional
    @Override
    public ProductResponse createProduct(Long categoryId,ProductRequest productRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, categoryId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(category.getRestaurant().getOwner().getId())){
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,category.getRestaurant().getName()));
        }

        Optional<Product> existingProduct =
                productRepository.findByCategoryIdAndNameIgnoreCase(
                        categoryId,
                        productRequest.getName().trim()
                );

        if (existingProduct.isPresent()) {

            Product product = existingProduct.get();
            product.setStock(product.getStock() + productRequest.getStock());

            if (product.getStock() > 0) {
                product.setActive(true);
            }
            return productMapper.toResponse(productRepository.save(product));
        }


        Product product = productMapper.toEntity(productRequest);
        product.setCategory(category);
        product.setActive(product.getStock() > 0);

        Product save = productRepository.save(product);

        return productMapper.toResponse(save);
    }

    /**
     * Updates product details.
     * Only restaurant owner can update.
     * Updates active status based on stock changes.
     */
    @Transactional
    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(
                product.getCategory().getRestaurant().getOwner().getId())){
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,
                    product.getCategory().getRestaurant().getName()));
        }

        productMapper.updateProductFromRequest(productRequest,product);

        if (productRequest.getStock() != null){
            product.setActive(product.getStock() > 0);
        }
        Product save = productRepository.save(product);

        return productMapper.toResponse(save);
    }

    /**
     * Soft deletes a product by setting it inactive.
     * Only restaurant owner can perform this action.
     */
    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(
                product.getCategory().getRestaurant().getOwner().getId())) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,product.getCategory().getRestaurant().getName()));
        }

        product.setActive(false);
        productRepository.save(product);
    }

    /**
     * Retrieves a product by ID.
     * If current user is restaurant owner, product is always visible.
     * Otherwise, product must be active and have stock > 0.
     */
    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

        User currentUser = null;

        try {
            currentUser = currentUserService.getCurrentUser();
        } catch (Exception ignored) {
        }

        if (currentUser != null &&
                currentUser.getId().equals(
                        product.getCategory().getRestaurant().getOwner().getId())) {
            return productMapper.toResponse(product);
        }

        if (!product.isActive() || product.getStock() <= 0) {
            throw new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, product.getName()));
        }
        return productMapper.toResponse(product);
    }

    /**
     * Toggles product's active status.
     * Cannot activate a product with 0 stock.
     * Only restaurant owner can perform this action.
     */
    @Transactional
    @Override
    public ProductResponse toggleProductStatus(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(
                product.getCategory().getRestaurant().getOwner().getId())) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,product.getCategory().getRestaurant().getName()));
        }

        if (!product.isActive() && product.getStock() == 0){
            throw new BaseException(new ErrorMessage(MessageType.PRODUCT_OUT_OF_STOCK,product.getName()));
        }

        product.setActive(!product.isActive());
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    /**
     * Updates the stock of a product.
     * Validates stock change and updates active status.
     * Only restaurant owner can perform this action.
     */
    @Transactional
    @Override
    public ProductResponse updateStock(Long productId,StockUpdateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(
                product.getCategory().getRestaurant().getOwner().getId())) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,product.getCategory().getRestaurant().getName()));
        }

        Integer change = request.getChange();
        if (change == null || change == 0) {
            throw new BaseException(
                    new ErrorMessage(MessageType.INVALID_STOCK_CHANGE, product.getName())
            );
        }
        int newStock = product.getStock() + change;

        if (newStock<0){
            throw new BaseException(new ErrorMessage(MessageType.INSUFFICIENT_STOCK,product.getName()));
        }

        product.setStock(newStock);
        product.setActive(newStock > 0);

        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    /**
     * Retrieves all active products for a given category with pagination.
     */
    @Override
    public RestPageableResponse<ProductResponse> getProductsByCategory(Long categoryId,
                                                                       RestPageableRequest pageableRequest) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND,categoryId.toString())));

        Pageable pageable = PagerUtil.toPageable(pageableRequest);

        Page<Product> page = productRepository
                .findByCategoryIdAndActiveTrue(categoryId, pageable);

        return PagerUtil.toPageResponse(page,productMapper::toResponse);
    }


}
