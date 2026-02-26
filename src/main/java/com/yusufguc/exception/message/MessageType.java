package com.yusufguc.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MessageType {

    VALIDATION_EXCEPTION("1000", "Validation error", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS("1001", "Username already exists", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS("1002","Invalid username or password",HttpStatus.CONFLICT),
    REFRESH_TOKEN_INVALID("1003","Refresh token invalid",HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED("1004","Refresh token expired",HttpStatus.UNAUTHORIZED),
    USERNAME_NOT_FOUND("1005", "Username not found", HttpStatus.NOT_FOUND),
    ALREADY_RESTAURANT_OWNER("1006", "User is already a restaurant owner", HttpStatus.CONFLICT),
    PENDING_OWNER_REQUEST_EXISTS("1007", "You already have a pending owner request", HttpStatus.CONFLICT),
    REQUEST_NOT_FOUND("1008", "Request not found", HttpStatus.NOT_FOUND),
    REQUEST_ALREADY_PROCESSED("1009", "Request has already been processed", HttpStatus.CONFLICT),
    RESTAURANT_NAME_ALREADY_EXISTS("1010", "Restaurant name already exists", HttpStatus.CONFLICT),
    RESTAURANT_NOT_FOUND("1011", "Restaurant not found", HttpStatus.NOT_FOUND),
    NOT_RESTAURANT_OWNER("1012", "This restaurant does not belong to you", HttpStatus.FORBIDDEN),
    CATEGORY_NAME_ALREADY_EXISTS("1013", "Category with name  already exists", HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND("1014", "Category not found with id", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND("3001", "Product not found with id ", HttpStatus.NOT_FOUND),
    PRODUCT_OUT_OF_STOCK("3002", "Product is out of stock", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_STOCK("3003", "Insufficient stock for product.", HttpStatus.BAD_REQUEST),
    INVALID_STOCK_CHANGE("3004", "Invalid stock change operation.", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND("4001", "Order not found with id: %s", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_COMPLETED("4002", "Order has already been completed and cannot be modified", HttpStatus.CONFLICT),
    INVALID_ORDER_STATUS_TRANSITION("4003", "Invalid status transition ", HttpStatus.BAD_REQUEST),
    ORDER_STATUS_CANNOT_BE_NULL("4004", "Order status cannot be null", HttpStatus.BAD_REQUEST),
    NOT_ORDER_OWNER("4005", "This order does not belong to you", HttpStatus.FORBIDDEN),
    ORDER_CANNOT_BE_CANCELLED("4006", "Order cannot be cancelled. Current status: ", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND("5001","cart not found",HttpStatus.BAD_REQUEST),
    CART_IS_EMPTY("5002","Cart is empty",HttpStatus.BAD_REQUEST),
    DIFFERENT_RESTAURANT_NOT_ALLOWED("5003", "Only products from the same restaurant can be added to the cart", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY("5004","Invalid quantity",HttpStatus.BAD_REQUEST),
    GENERAL_EXCEPTION("9999", "A general error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

    private final  String code;
    private final  String message;
    private final HttpStatus httpStatus;

    MessageType(String code,String message,HttpStatus httpStatus){
        this.code=code;
        this.message=message;
        this.httpStatus=httpStatus;
    }
}
