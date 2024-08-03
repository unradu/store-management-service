package com.demo.store.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResultDTO {
    private Long id;
    private Long productId;
    private Long quantity;
    private BigDecimal cost;
    private Integer version;
}
