package com.yusufguc.service.impl;

import com.yusufguc.dto.request.CategoryRequest;
import com.yusufguc.dto.response.CategoryResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.mapper.CategoryMapper;
import com.yusufguc.model.Category;
import com.yusufguc.model.Restaurant;
import com.yusufguc.model.User;
import com.yusufguc.pagination.PagerUtil;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.repository.CategoryRepository;
import com.yusufguc.repository.RestaurantRepository;
import com.yusufguc.security.CurrentUserService;
import com.yusufguc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final CurrentUserService currentUserService;
    private final CategoryMapper categoryMapper;

    /**
     * Creates a new category for a restaurant.
     * Checks if current user is the restaurant owner.
     * Throws exception if restaurant not found or category name already exists.
     */
    @Transactional
    @Override
    public CategoryResponse createCategory(Long restaurantId, CategoryRequest request) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(restaurant.getOwner().getId())){
            throw  new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,restaurantId.toString()));
        }

        if (categoryRepository.existsByNameIgnoreCaseAndRestaurant_Id(request.getName().trim(),restaurantId)){
            throw new BaseException(new ErrorMessage(MessageType.CATEGORY_NAME_ALREADY_EXISTS,request.getName()));
        }

        Category category = categoryMapper.toEntity(request);
        category.setRestaurant(restaurant);

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    /**
     * Updates an existing category.
     * Checks ownership and ensures new name doesn't conflict with existing categories.
     */
    @Transactional
    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, categoryId.toString())));


        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(category.getRestaurant().getOwner().getId())){
            throw  new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,category.getRestaurant().getName()));
        }

        if(categoryRepository
                .existsByNameIgnoreCaseAndRestaurant_IdAndIdNot(
                        request.getName().trim(),category.getRestaurant().getId(),categoryId)) {
            throw new BaseException(new ErrorMessage(MessageType.CATEGORY_NAME_ALREADY_EXISTS,request.getName()));
        }
        categoryMapper.updateEntityFromRequest(request,category);
        Category saved = categoryRepository.save(category);

        return categoryMapper.toResponse(saved);
    }

    /**
     * Deletes a category.
     * Checks ownership and throws exception if category or authorization fails.
     */
    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, categoryId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(category.getRestaurant().getOwner().getId())){
            throw  new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,category.getRestaurant().getName()));
        }

        categoryRepository.delete(category);
    }

    /**
     * Retrieves a category by its ID.
     * Throws exception if not found.
     */
    @Override
    public CategoryResponse getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, categoryId.toString())));

        return categoryMapper.toResponse(category);
    }

    /**
     * Retrieves paginated list of categories for a restaurant.
     * Throws exception if restaurant not found.
     */
    @Override
    public RestPageableResponse<CategoryResponse> getCategoriesByRestaurant(Long restaurantId, RestPageableRequest request) {

       restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        Pageable pageable = PagerUtil.toPageable(request);

        Page<Category> categoryPage = categoryRepository.findByRestaurantId(restaurantId, pageable);


        return PagerUtil.toPageResponse(categoryPage, categoryMapper::toResponse);
    }

    /**
     * Retrieves paginated list of categories for current user's restaurant.
     * Checks ownership before returning results.
     */
    @Override
    public RestPageableResponse<CategoryResponse> getMyCategories(Long restaurantId, RestPageableRequest request) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(restaurant.getOwner().getId())){
            throw  new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,restaurant.getName()));
        }
        Pageable pageable = PagerUtil.toPageable(request);

        Page<Category> categoryPage = categoryRepository.findByRestaurantId(restaurantId, pageable);
        return PagerUtil.toPageResponse(categoryPage, categoryMapper::toResponse);
    }

    /**
     * Searches categories by name for a restaurant with pagination.
     * Ensures current user is the restaurant owner.
     */
    @Override
    public RestPageableResponse<CategoryResponse> searchByName(Long restaurantId,String name, RestPageableRequest request) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(restaurant.getOwner().getId())){
            throw  new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,restaurant.getName()));
        }

        Pageable pageable = PagerUtil.toPageable(request);
        Page<Category> categoriesByName = categoryRepository
                .findByRestaurantIdAndNameContainingIgnoreCase(restaurantId,name.trim(), pageable);

        return PagerUtil.toPageResponse(categoriesByName,categoryMapper::toResponse);
    }






}
