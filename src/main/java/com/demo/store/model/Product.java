package com.demo.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    public static final String UNIQUE_NAME_INDEX = "product.name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "discounted_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountedPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "product_state", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private ProductState state;

    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "modified_at", nullable = false)
    @LastModifiedDate
    private Instant modifiedAt;

    @Column(name = "created_by", nullable = false, length = 64, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by", length = 64, updatable = false)
    @LastModifiedBy
    private String modifiedBy;
}
