package com.demo.store.api;

import com.demo.store.dto.AddProductDTO;
import com.demo.store.dto.AddProductResultDTO;
import com.demo.store.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<AddProductResultDTO> addProduct(@Valid @RequestBody AddProductDTO addProductDTO) {
        AddProductResultDTO resultDTO = productService.addProduct(addProductDTO);
        return ResponseEntity.ok(resultDTO);
    }
}
