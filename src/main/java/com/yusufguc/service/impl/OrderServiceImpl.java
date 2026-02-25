package com.yusufguc.service.impl;

import com.yusufguc.dto.request.OrderItemRequest;
import com.yusufguc.dto.request.OrderRequest;
import com.yusufguc.dto.response.OrderResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.mapper.OrderMapper;
import com.yusufguc.model.*;
import com.yusufguc.model.enums.OrderStatus;
import com.yusufguc.model.enums.Role;
import com.yusufguc.pagination.PagerUtil;
import com.yusufguc.pagination.RestPageableRequest;
import com.yusufguc.pagination.RestPageableResponse;
import com.yusufguc.repository.OrderRepository;
import com.yusufguc.repository.ProductRepository;
import com.yusufguc.repository.RestaurantRepository;
import com.yusufguc.security.CurrentUserService;
import com.yusufguc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final CurrentUserService currentUserService;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        Restaurant restaurant = restaurantRepository.findById(orderRequest.getRestaurantId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND,
                        orderRequest.getRestaurantId().toString())));

        User currentUser = currentUserService.getCurrentUser();

        Order order=new Order();
        order.setUser(currentUser);
        order.setRestaurant(restaurant);

        List<OrderItem> orderItems=new ArrayList<>();

        for (OrderItemRequest itemRequest: orderRequest.getItems()){

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND,
                            itemRequest.getProductId().toString())));

            if (!product.isActive() || product.getStock()< itemRequest.getQuantity()){
                throw new BaseException(new ErrorMessage(MessageType.INSUFFICIENT_STOCK,product.getName()));
            }

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            OrderItem orderItem=new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);
        }
        order.setItems(orderItems);
        order.calculateTotalPrice();

        orderRepository.save(order);
        return orderMapper.toResponse(order);

    }

    @Transactional
    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {

        if (newStatus==null){
            throw new BaseException(new ErrorMessage(MessageType.ORDER_STATUS_CANNOT_BE_NULL,null));
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.ORDER_NOT_FOUND, orderId.toString())
                ));

        User currentUser = currentUserService.getCurrentUser();

        if (!currentUser.getId().equals(order.getRestaurant().getOwner().getId())){
            throw  new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,order.getRestaurant().getName()));
        }

        OrderStatus currentStatus = order.getStatus();

        if (currentStatus == OrderStatus.DELIVERED ||
                currentStatus == OrderStatus.CANCELLED) {

            throw new BaseException(new ErrorMessage(MessageType.ORDER_ALREADY_COMPLETED, orderId.toString()));
        }

        if (currentStatus == newStatus) {
            return orderMapper.toResponse(order);
        }

        boolean validTransition = switch (currentStatus) {
            case PENDING ->
                    newStatus == OrderStatus.CONFIRMED ||
                            newStatus == OrderStatus.CANCELLED;
            case CONFIRMED ->
                    newStatus == OrderStatus.PREPARING;
            case PREPARING ->
                    newStatus == OrderStatus.OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY ->
                    newStatus == OrderStatus.DELIVERED;
            default -> false;
        };

        if (!validTransition) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_ORDER_STATUS_TRANSITION,
                    currentStatus + " -> " + newStatus));
        }
        order.setStatus(newStatus);
        orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    @Transactional
    @Override
    public OrderResponse cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.ORDER_NOT_FOUND, orderId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(order.getUser().getId())){
            throw new BaseException(new ErrorMessage(MessageType.NOT_ORDER_OWNER,orderId.toString()));
        }

        if (order.getStatus()!=OrderStatus.PENDING){
            throw new BaseException(new ErrorMessage(MessageType.ORDER_CANNOT_BE_CANCELLED,order.getStatus().toString()));
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public RestPageableResponse<OrderResponse> getMyOrders(RestPageableRequest pageableRequest) {

        User currentUser = currentUserService.getCurrentUser();

        Pageable pageable = PagerUtil.toPageable(pageableRequest);

        Page<Order> orderPage = orderRepository.findByUserId(currentUser.getId(), pageable);

        return PagerUtil.toPageResponse(orderPage,orderMapper::toResponse);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.ORDER_NOT_FOUND, orderId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        boolean isUserOwner = currentUser.getRole() == Role.USER && currentUser.getId().equals(order.getUser().getId());
        boolean isRestaurantOwner = currentUser.getId().equals(order.getRestaurant().getOwner().getId());

        if (!isUserOwner && !isRestaurantOwner) {
            throw new BaseException(
                    new ErrorMessage(currentUser.getRole() == Role.USER ? MessageType.NOT_ORDER_OWNER : MessageType.NOT_RESTAURANT_OWNER,
                    orderId.toString()
            ));
        }
        return orderMapper.toResponse(order);
    }

    @Override
    public RestPageableResponse<OrderResponse> getOrdersByRestaurant(Long restaurantId,RestPageableRequest pageableRequest) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESTAURANT_NOT_FOUND, restaurantId.toString())));

        User currentUser = currentUserService.getCurrentUser();
        if (!currentUser.getId().equals(restaurant.getOwner().getId())){
            throw new BaseException(new ErrorMessage(MessageType.NOT_RESTAURANT_OWNER,restaurantId.toString()));
        }

        Pageable pageable = PagerUtil.toPageable(pageableRequest);

        Page<Order> orderPage = orderRepository.findByRestaurant(restaurant, pageable);

        return PagerUtil.toPageResponse(orderPage,orderMapper::toResponse);
    }


}
