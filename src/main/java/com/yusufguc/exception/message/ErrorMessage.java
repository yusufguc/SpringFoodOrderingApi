package com.yusufguc.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private MessageType messageType;
    private String detail;

    public String prepareErrorMessage() {
        if (detail == null) {
            return messageType.getMessage();
        }
        return messageType.getMessage() + " : " + detail;
    }
}
