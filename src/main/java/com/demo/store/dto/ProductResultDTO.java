package com.demo.store.dto;

import com.demo.store.model.ProductState;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResultDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private Integer quantity;
    private ProductState state;
    private Integer version;
}