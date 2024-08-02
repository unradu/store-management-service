package com.demo.store.service;

import com.demo.store.dto.AddProductDTO;
import com.demo.store.dto.AddProductResultDTO;
import com.demo.store.dto.mapper.ProductMapper;
import com.demo.store.model.Product;
import com.demo.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public AddProductResultDTO addProduct(AddProductDTO addProductDTO) {
        Product product = productMapper.addProductDtoToProduct(addProductDTO);
        product = productRepository.save(product);
        return productMapper.productToAddProductResultDto(product);
    }
}
