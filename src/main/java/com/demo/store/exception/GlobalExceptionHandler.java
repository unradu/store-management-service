package com.demo.store.exception;

import com.demo.store.exception.custom.BusinessException;
import com.demo.store.exception.custom.PropertyConflictException;
import com.demo.store.exception.custom.ResourceNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleFieldValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return createErrorDetailsResponse("Field Validation Exception", HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return createErrorDetailsResponse("Invalid Request Body", HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler(PropertyConflictException.class)
    public final ResponseEntity<Object> handlePropertyConflictException(PropertyConflictException ex) {
        return createErrorDetailsResponse("Conflicting property value", HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return createErrorDetailsResponse("Resource not found", HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        return createErrorDetailsResponse("Business error", HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private static ResponseEntity<Object> createErrorDetailsResponse(String title, HttpStatus httpStatus,
                                                                             Object error) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("title", title);
        errorDetails.put("status", httpStatus.value());
        errorDetails.put("error", error);
        return new ResponseEntity<>(errorDetails, httpStatus);
    }
}
