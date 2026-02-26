package com.yusufguc.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;
/**
 * Enumeration of application error messages and status codes.
 * Organized by functional modules for better error tracking and client feedback.
 */
@Getter
public enum MessageType {

    // --- GENERAL & VALIDATION (1000 - 1999) ---
    GENERAL_EXCEPTION("GEN-1000", "A general error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_EXCEPTION("GEN-1001", "Validation error", HttpStatus.BAD_REQUEST),

    // --- AUTH & USER (2000 - 2999) ---
    USERNAME_ALREADY_EXISTS("USR-2000", "Username already exists", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS("USR-2001", "Invalid username or password", HttpStatus.UNAUTHORIZED),
    USERNAME_NOT_FOUND("USR-2002", "Username not found", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_INVALID("USR-2003", "Refresh token invalid", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED("USR-2004", "Refresh token expired", HttpStatus.UNAUTHORIZED),

    // --- RESTAURANT & OWNER REQUESTS (3000 - 3999) ---
    RESTAURANT_NOT_FOUND("RST-3000", "Restaurant not found", HttpStatus.NOT_FOUND),
    RESTAURANT_NAME_ALREADY_EXISTS("RST-3001", "Restaurant name already exists", HttpStatus.CONFLICT),
    ALREADY_RESTAURANT_OWNER("RST-3002", "User is already a restaurant owner", HttpStatus.CONFLICT),
    NOT_RESTAURANT_OWNER("RST-3003", "This restaurant does not belong to you", HttpStatus.FORBIDDEN),
    PENDING_OWNER_REQUEST_EXISTS("RST-3004", "You already have a pending owner request", HttpStatus.CONFLICT),
    REQUEST_NOT_FOUND("RST-3005", "Request not found", HttpStatus.NOT_FOUND),
    REQUEST_ALREADY_PROCESSED("RST-3006", "Request has already been processed", HttpStatus.CONFLICT),

    // --- CATEGORY & PRODUCT (4000 - 4999) ---
    CATEGORY_NOT_FOUND("PRD-4000", "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_ALREADY_EXISTS("PRD-4001", "Category name already exists", HttpStatus.CONFLICT),
    PRODUCT_NOT_FOUND("PRD-4002", "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_OUT_OF_STOCK("PRD-4003", "Product is out of stock", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_STOCK("PRD-4004", "Insufficient stock for product", HttpStatus.BAD_REQUEST),
    INVALID_STOCK_CHANGE("PRD-4005", "Invalid stock change operation", HttpStatus.BAD_REQUEST),

    // --- CART (5000 - 5999) ---
    CART_NOT_FOUND("CRT-5000", "Cart not found", HttpStatus.NOT_FOUND),
    CART_IS_EMPTY("CRT-5001", "Cart is empty", HttpStatus.BAD_REQUEST),
    DIFFERENT_RESTAURANT_NOT_ALLOWED("CRT-5002", "Only products from the same restaurant can be added to the cart", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY("CRT-5003", "Invalid quantity", HttpStatus.BAD_REQUEST),

    // --- ORDER (6000 - 6999) ---
    ORDER_NOT_FOUND("ORD-6000", "Order not found", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_COMPLETED("ORD-6001", "Order has already been completed and cannot be modified", HttpStatus.CONFLICT),
    INVALID_ORDER_STATUS_TRANSITION("ORD-6002", "Invalid status transition", HttpStatus.BAD_REQUEST),
    ORDER_STATUS_CANNOT_BE_NULL("ORD-6003", "Order status cannot be null", HttpStatus.BAD_REQUEST),
    NOT_ORDER_OWNER("ORD-6004", "This order does not belong to you", HttpStatus.FORBIDDEN),
    ORDER_CANNOT_BE_CANCELLED("ORD-6005", "Order cannot be cancelled in its current status", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    MessageType(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
