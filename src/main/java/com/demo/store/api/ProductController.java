package com.demo.store.api;

import com.demo.store.dto.AddProductDTO;
import com.demo.store.dto.ProductPriceChangeDTO;
import com.demo.store.dto.ProductResultDTO;
import com.demo.store.model.ProductState;
import com.demo.store.service.ProductPricingService;
import com.demo.store.service.ProductLifecycleService;
import com.demo.store.service.ProductRetrievalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductLifecycleService productLifecycleService;
    private final ProductPricingService productPricingService;
    private final ProductRetrievalService productRetrievalService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResultDTO> addProduct(@Valid @RequestBody AddProductDTO addProductDTO) {
        ProductResultDTO resultDTO = productLifecycleService.addProduct(addProductDTO);
        return ResponseEntity.ok(resultDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_ASSOCIATE')")
    public ResponseEntity<PagedModel<ProductResultDTO>> getAllProducts(
                                                                    @RequestParam(required = false) ProductState state,
                                                                    @RequestParam(defaultValue = "0") int pageNumber,
                                                                    @RequestParam(defaultValue = "20") int pageSize) {
        Page<ProductResultDTO> resultDTO = productRetrievalService.getAllProducts(state, pageNumber, pageSize);
        return ResponseEntity.ok(new PagedModel<>(resultDTO));
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_ASSOCIATE')")
    public ResponseEntity<ProductResultDTO> getProduct(@PathVariable Long productId) {
        ProductResultDTO productResultDTO = productRetrievalService.getProduct(productId);
        return ResponseEntity.ok(productResultDTO);
    }

    @PutMapping("/{productId}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResultDTO> changePrice(@PathVariable Long productId,
                                                        @Valid @RequestBody ProductPriceChangeDTO productPriceChangeDTO) {
        ProductResultDTO productResultDTO = productPricingService.changePrice(productId, productPriceChangeDTO);
        return ResponseEntity.ok(productResultDTO);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeProduct(@PathVariable Long productId) {
        productLifecycleService.removeProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
