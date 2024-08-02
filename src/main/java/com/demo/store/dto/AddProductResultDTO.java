package com.demo.store.dto;

import com.demo.store.model.CurrencyEnum;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddProductResultDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private CurrencyEnum currency;
    private Integer quantity;
    private Integer version;
}