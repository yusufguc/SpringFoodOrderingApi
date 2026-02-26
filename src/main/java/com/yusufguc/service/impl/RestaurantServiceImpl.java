package com.yusufguc.service.impl;

import com.yusufguc.dto.request.RestaurantRequest;
import com.yusufguc.dto.response.RestaurantResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.mapper.RestaurantMapper;
import com.yusufguc.model.Restaurant;
import com.yusufguc.model.User;
import com.yusufguc.pagination.PagerUtil;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.repository.RestaurantRepository;
import com.yusufguc.security.CurrentUserService;
import com.yusufguc.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final CurrentUserService currentUserService;

    /**
     * Creates a new restaurant and assigns the current user as owner.
     * Throws exception if restaurant name already exists.
     */
    @Transactional
    @Override
    public RestaurantResponse createRestaurant(RestaurantRequest request) {

         if (restaurantRepository.existsByName(request.getName())){
             throw new BaseException(new ErrorMessage(MessageType.RESTAURANT_NAME_ALREADY_EXISTS,request.getName()));
         }

        Restaurant restaurant = restaurantMapper.toEntity(request);

        User user=currentUserService.getCurrentUser();
        restaurant.setOwner(user);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.toResponse(savedRestaurant);
    }

    /**
     * Updates restaurant information. Only the owner can update.
     * Throws exception if name is already taken by another restaurant.
     */
    @Transactional
    @Override
    public RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request) {

        Restaurant restaurantDb = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(restaurantDb.getOwner().getId())){
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,null));
        }

        if (restaurantRepository.existsByNameAndIdNot(request.getName(),restaurantId)){
            throw new BaseException(new ErrorMessage(MessageType.RESTAURANT_NAME_ALREADY_EXISTS,request.getName()));
        }

        restaurantMapper.updateRestaurantFromDto(request,restaurantDb);
        Restaurant savedRestaurant = restaurantRepository.save(restaurantDb);

        return restaurantMapper.toResponse(savedRestaurant);
    }

    /**
     * Deletes a restaurant. Only the owner can delete.
     */
    @Transactional
    @Override
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurantDb = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(restaurantDb.getOwner().getId())){
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,null));
        }
        restaurantRepository.delete(restaurantDb);
    }

    /**
     * Retrieves all restaurants with pagination.
     */
    @Override
    public RestPageableResponse<RestaurantResponse> getAllRestaurant(
            RestPageableRequest request) {

        Pageable pageable = PagerUtil.toPageable(request);

        Page<Restaurant> page = restaurantRepository.findAll(pageable);

        return PagerUtil.toPageResponse(page, restaurantMapper::toResponse);
    }

    /**
     * Searches restaurants by name. Returns all if name is null or blank.
     */
    @Override
    public RestPageableResponse<RestaurantResponse> searchByName(String name, RestPageableRequest request) {
        Pageable pageable = PagerUtil.toPageable(request);

        Page<Restaurant> page;

        if (name == null || name.isBlank()) {
            page = restaurantRepository.findAll(pageable);
        } else {
            page = restaurantRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        return PagerUtil.toPageResponse(page, restaurantMapper::toResponse);
    }

    /**
     * Filters restaurants based on open status (open or closed) with pagination.
     */
    @Override
    public RestPageableResponse<RestaurantResponse> filterByOpenStatus(boolean open, RestPageableRequest request) {

        Pageable pageable = PagerUtil.toPageable(request);

        Page<Restaurant> page = restaurantRepository.findByOpen(open, pageable);

        return PagerUtil.toPageResponse(page, restaurantMapper::toResponse);
    }

    /**
     * Retrieves restaurants owned by current user with pagination.
     */
    @Override
    public RestPageableResponse<RestaurantResponse> getMyRestaurants(RestPageableRequest request) {
        Pageable pageable = PagerUtil.toPageable(request);

        User currentUser = currentUserService.getCurrentUser();

        Page<Restaurant> page = restaurantRepository.findByOwnerId(currentUser.getId(), pageable);

        return PagerUtil.toPageResponse(page, restaurantMapper::toResponse);
    }

    /**
     * Toggles the open status of the restaurant. Only the owner can perform this.
     * Returns the updated restaurant response.
     */
    @Transactional
    @Override
    public RestaurantResponse toggleOpenStatus(Long restaurantId) {

        Restaurant restaurantDb = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(restaurantDb.getOwner().getId())){
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,null));
        }

        restaurantDb.setOpen(!restaurantDb.isOpen());
        Restaurant saved = restaurantRepository.save(restaurantDb);

        return restaurantMapper.toResponse(saved);
    }


}
