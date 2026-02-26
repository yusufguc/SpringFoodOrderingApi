package com.yusufguc.service.impl;

import com.yusufguc.dto.request.CartItemRequest;
import com.yusufguc.dto.request.OrderItemRequest;
import com.yusufguc.dto.request.OrderRequest;
import com.yusufguc.dto.response.CartResponse;
import com.yusufguc.dto.response.OrderResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.mapper.CartMapper;
import com.yusufguc.mapper.CheckoutMapper;
import com.yusufguc.model.Cart;
import com.yusufguc.model.CartItem;
import com.yusufguc.model.Product;
import com.yusufguc.model.User;
import com.yusufguc.repository.CartRepository;
import com.yusufguc.repository.ProductRepository;
import com.yusufguc.security.CurrentUserService;
import com.yusufguc.service.CartService;
import com.yusufguc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;
    private final CartMapper cartMapper;
    private final CheckoutMapper checkoutMapper;
    private final OrderService orderService;


    /**
     * Adds a product to the current user's cart.
     * If the user has no cart, creates a new one.
     * Throws exception if the product belongs to a different restaurant than the existing cart.
     */
    @Transactional
    @Override
    public CartResponse addToCart(CartItemRequest request) {
        User currentUser = currentUserService.getCurrentUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, request.getProductId().toString())));

        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(currentUser);
                    newCart.setRestaurant(product.getCategory().getRestaurant());
                    newCart.setItems(new ArrayList<>());
                    newCart.setTotalPrice(BigDecimal.ZERO);
                    return newCart;
                });

        if (!cart.getRestaurant().getId().equals(product.getCategory().getRestaurant().getId())) {
             throw new BaseException(new ErrorMessage(MessageType.DIFFERENT_RESTAURANT_NOT_ALLOWED, cart.getRestaurant().getId().toString()));
        }

        updateOrCreateCartItem(cart, product, request.getQuantity());

        calculateTotalPrice(cart);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    /**
     * Removes a specified quantity of a product from the user's cart.
     * Deletes the cart if it becomes empty after removal.
     */
    @Transactional
    @Override
    public CartResponse removeFromCart(Long productId, Integer quantity) {
        User currentUser = currentUserService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CART_NOT_FOUND, null)));

        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

        if (quantity <= 0) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_QUANTITY, null));
        }
        if (itemToRemove.getQuantity() <= quantity) {
            cart.getItems().remove(itemToRemove);
        } else {
            itemToRemove.setQuantity(itemToRemove.getQuantity() - quantity);
        }

        if (cart.getItems().isEmpty()) {
            cartRepository.delete(cart);
            CartResponse emptyResponse = new CartResponse();
            emptyResponse.setItems(new ArrayList<>());
            emptyResponse.setTotalPrice(BigDecimal.ZERO);
            return emptyResponse;
        }

        calculateTotalPrice(cart);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    /**
     * Retrieves the current user's cart.
     * Returns empty cart if none exists.
     */
    @Override
    public CartResponse getMyCart() {
        User currentUser = currentUserService.getCurrentUser();

        return cartRepository.findByUserId(currentUser.getId())
                .map(cartMapper::toCartResponse)
                .orElseGet(() -> {
                    CartResponse empty = new CartResponse();
                    empty.setItems(new ArrayList<>());
                    empty.setTotalPrice(BigDecimal.ZERO);
                    return empty;
                });
    }

    /**
     * Clears all items from the current user's cart.
     */
    @Transactional
    @Override
    public void clearCart() {
        User currentUser = currentUserService.getCurrentUser();
        cartRepository.deleteByUserId(currentUser.getId());
    }

    /**
     * Converts the current cart into an order and clears the cart.
     * Throws exception if the cart is empty or not found.
     */
    @Transactional
    @Override
    public OrderResponse checkout() {
        User currentUser = currentUserService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CART_NOT_FOUND, null)));

        if (cart.getItems().isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.CART_IS_EMPTY, null));
        }

        List<OrderItemRequest> orderItems = checkoutMapper.cartItemsToOrderItems(cart.getItems());

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setRestaurantId(cart.getRestaurant().getId());
        orderRequest.setItems(orderItems);

        OrderResponse response = orderService.createOrder(orderRequest);

        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);

        return response;
    }

    /**
     * Updates an existing cart item or adds a new one if it doesn't exist.
     */
    private void updateOrCreateCartItem(Cart cart, Product product, Integer quantity) {
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + quantity),
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setCart(cart);
                            newItem.setProduct(product);
                            newItem.setQuantity(quantity);
                            newItem.setPrice(product.getPrice());
                            cart.getItems().add(newItem);
                        }
                );
    }

    /**
     * Calculates the total price of the cart based on item quantities and prices.
     */
    private void calculateTotalPrice(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
    }
}