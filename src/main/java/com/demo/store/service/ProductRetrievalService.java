package com.demo.store.service;

import com.demo.store.dto.ProductResultDTO;
import com.demo.store.dto.mapper.ProductMapper;
import com.demo.store.exception.custom.ResourceNotFoundException;
import com.demo.store.model.Product;
import com.demo.store.model.ProductState;
import com.demo.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductRetrievalService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Page<ProductResultDTO> getAllProducts(ProductState state, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage = productRepository.findAllByOptionalState(state, pageable);
        List<ProductResultDTO> productDTOs = productPage.getContent().stream()
                .map(productMapper::productToProductResultDto)
                .collect(Collectors.toList());
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    public ProductResultDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow( () ->
                new ResourceNotFoundException(String.format("Product with id [%s] could not be found", productId))
        );
        return productMapper.productToProductResultDto(product);
    }
}
