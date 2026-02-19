package com.yusufguc.exception.handler;

import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.model.ApiError;
import com.yusufguc.exception.model.ExceptionDetail;
import com.yusufguc.exception.message.MessageType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError<String>> handleBaseException(
            BaseException exception,
            WebRequest request) {

        MessageType messageType = exception.getMessageType();

        ApiError<String> apiError = createApiError(
                exception.getMessage(),
                messageType,
                request
        );

        return ResponseEntity
                .status(messageType.getHttpStatus())
                .body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleValidationException(
            MethodArgumentNotValidException exception,
            WebRequest request) {

        Map<String, List<String>> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    errors.computeIfAbsent(
                            fieldError.getField(),
                            key -> new ArrayList<>()
                    ).add(fieldError.getDefaultMessage());
                });

        ApiError<Map<String, List<String>>> apiError =
                createApiError(errors, MessageType.GENERAL_EXCEPTION, request);

        return ResponseEntity
                .badRequest()
                .body(apiError);
    }

    private <E> ApiError<E> createApiError(
            E message,
            MessageType messageType,
            WebRequest request) {

        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(messageType.getHttpStatus().value());
        apiError.setErrorCode(messageType.getCode());

        ExceptionDetail<E> detail = new ExceptionDetail<>();
        detail.setCreateTime(new Date());
        detail.setHostName(getHostname());
        detail.setPath(request.getDescription(false).substring(4));
        detail.setMessage(message);

        apiError.setException(detail);

        return apiError;
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
