package com.demo.store.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.util.List;

@Builder
@Data
public class ErrorDetails {
    private String title;
    private HttpStatus httpStatus;
    private List<String> errors;
}
