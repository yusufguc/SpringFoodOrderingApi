package com.yusufguc.exception.base;

import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import lombok.Getter;
@Getter
public class BaseException extends RuntimeException{

    private final MessageType messageType;

    public BaseException(ErrorMessage errorMessage){
        super(errorMessage.prepareErrorMessage());
        this.messageType = errorMessage.getMessageType();
    }
}
