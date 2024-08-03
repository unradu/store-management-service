package com.demo.store.exception.custom;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
