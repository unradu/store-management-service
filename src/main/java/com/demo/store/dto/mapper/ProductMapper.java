package com.demo.store.dto.mapper;

import com.demo.store.dto.AddProductDTO;
import com.demo.store.dto.AddProductResultDTO;
import com.demo.store.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product addProductDtoToProduct(AddProductDTO dto);

    AddProductResultDTO productToAddProductResultDto(Product product);
}
