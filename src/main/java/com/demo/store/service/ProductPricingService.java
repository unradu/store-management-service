package com.demo.store.service;

import com.demo.store.dto.ProductPriceChangeDTO;
import com.demo.store.dto.ProductResultDTO;
import com.demo.store.dto.mapper.ProductMapper;
import com.demo.store.exception.custom.ResourceNotFoundException;
import com.demo.store.model.Product;
import com.demo.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ProductPricingService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResultDTO changePrice(Long productId, ProductPriceChangeDTO priceChangeDTO) {
        Product product = productRepository.findById(productId).orElseThrow( () ->
                new ResourceNotFoundException(String.format("Product with id [%s] could not be found", productId))
        );
        if (priceChangeDTO.getDiscount() == null) {
            product.setPrice(priceChangeDTO.getPrice());
            product.setDiscount(null);
            product.setDiscountedPrice(null);
        } else {
            product.setPrice(priceChangeDTO.getPrice());
            product.setDiscount(priceChangeDTO.getDiscount());
            product.setDiscountedPrice(computeDiscountedPrice(priceChangeDTO.getPrice(), priceChangeDTO.getDiscount()));
        }
        productRepository.save(product);
        return productMapper.productToProductResultDto(product);
    }

    private BigDecimal computeDiscountedPrice(BigDecimal fullPrice, Integer discount) {
        BigDecimal discountPercentage = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = fullPrice.multiply(discountPercentage);
        return fullPrice.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
