package com.demo.store.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import java.util.List;

@Builder
public class ErrorDetails {
    private String title;
    private HttpStatus httpStatus;
    private List<String> errors;
}
