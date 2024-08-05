package com.demo.store.dto.mapper;

import com.demo.store.dto.AddProductDTO;
import com.demo.store.dto.ProductResultDTO;
import com.demo.store.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product addProductDtoToProduct(AddProductDTO dto);

    ProductResultDTO productToProductResultDto(Product product);
}
