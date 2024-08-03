package com.demo.store.service;

import com.demo.store.dto.AddProductDTO;
import com.demo.store.dto.ProductResultDTO;
import com.demo.store.dto.mapper.ProductMapper;
import com.demo.store.exception.custom.PropertyConflictException;
import com.demo.store.exception.custom.ResourceNotFoundException;
import com.demo.store.model.Product;
import com.demo.store.model.ProductState;
import com.demo.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLifecycleService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResultDTO addProduct(AddProductDTO addProductDTO) {
        Product product = productMapper.addProductDtoToProduct(addProductDTO);
        ProductState state = product.getQuantity() == 0 ? ProductState.OUT_OF_STOCK : ProductState.AVAILABLE;
        product.setState(state);
        try {
            product = productRepository.save(product);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException cvex) {
                if (Product.UNIQUE_NAME_INDEX.equals(cvex.getConstraintName())) {
                    throw new PropertyConflictException("A product with this name already exists");
                }
            }
            throw ex;
        }
        return productMapper.productToProductResultDto(product);
    }

    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow( () ->
                new ResourceNotFoundException(String.format("Product with id [%s] could not be found", productId))
        );
        product.setState(ProductState.REMOVED);
        productRepository.save(product);
    }
}
