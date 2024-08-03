package com.demo.store.exception.custom;

public class PropertyConflictException extends RuntimeException {
    public PropertyConflictException(String message) {
        super(message);
    }
}
