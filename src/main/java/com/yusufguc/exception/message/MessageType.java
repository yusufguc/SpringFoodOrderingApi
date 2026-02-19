package com.yusufguc.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MessageType {
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
