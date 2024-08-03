package com.demo.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GenerateOrderDTO {
    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;

    @NotEmpty
    @Email
    private String buyerEmail;
}
