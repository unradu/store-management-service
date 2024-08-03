package com.demo.store.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPriceChangeDTO {
    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @Max(99)
    @Min(1)
    private Integer discount;
}
