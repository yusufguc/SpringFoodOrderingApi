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
