package com.demo.store.dto;

import com.demo.store.model.CurrencyEnum;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductDTO {
    @NotBlank
    @Size(max = 64)
    private String name;

    @Size(max = 256)
    private String description;

    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull
    private CurrencyEnum currency;

    @NotNull
    @PositiveOrZero
    private Integer quantity;
}