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
