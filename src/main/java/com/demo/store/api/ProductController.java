package com.demo.store.api;

import com.demo.store.dto.AddProductDTO;
import com.demo.store.dto.ProductPriceChangeDTO;
import com.demo.store.dto.ProductResultDTO;
import com.demo.store.exception.ErrorDetails;
import com.demo.store.model.ProductState;
import com.demo.store.service.ProductPricingService;
import com.demo.store.service.ProductLifecycleService;
import com.demo.store.service.ProductRetrievalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Add product",
            description = "Used to insert a new Product. Name must not be empty, price and quantity must be positive." +
                    "The operation can be triggered by the following role(s): 'ADMIN'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product inserted successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                            useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class))),
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResultDTO> addProduct(@Valid @RequestBody AddProductDTO addProductDTO) {
        ProductResultDTO resultDTO = productLifecycleService.addProduct(addProductDTO);
        return ResponseEntity.ok(resultDTO);
    }

    @Operation(summary = "Get all products",
            description = "Retrieves all products in a paged format. The request accepts the product state as " +
                    "an optional parameter. " +
                    "The operation can be triggered by the following role(s): 'ADMIN' and 'SALES_ASSOCIATE'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product inserted successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                            useReturnTypeSchema = true)
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_ASSOCIATE')")
    public ResponseEntity<PagedModel<ProductResultDTO>> getAllProducts(
                                                                    @RequestParam(required = false) ProductState state,
                                                                    @RequestParam(defaultValue = "0") int pageNumber,
                                                                    @RequestParam(defaultValue = "20") int pageSize) {
        Page<ProductResultDTO> resultDTO = productRetrievalService.getAllProducts(state, pageNumber, pageSize);
        return ResponseEntity.ok(new PagedModel<>(resultDTO));
    }

    @Operation(summary = "Get product",
            description = "Retrieves a single product by using its identifier. " +
                    "The operation can be triggered by the following role(s): 'ADMIN' and 'SALES_ASSOCIATE'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product inserted successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_ASSOCIATE')")
    public ResponseEntity<ProductResultDTO> getProduct(@PathVariable Long productId) {
        ProductResultDTO productResultDTO = productRetrievalService.getProduct(productId);
        return ResponseEntity.ok(productResultDTO);
    }

    @Operation(summary = "Change product price",
            description = "Used to modify the price of a product. Can also be used for applying discounts. " +
                    "If no discount is desired leave the attribute as null. Price and discount must be positive. " +
                    "The operation can be triggered by the following role(s): 'ADMIN'.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price changed successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                            useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class))),
    })
    @PutMapping("/{productId}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResultDTO> changePrice(@PathVariable Long productId,
                                                        @Valid @RequestBody ProductPriceChangeDTO productPriceChangeDTO) {
        ProductResultDTO productResultDTO = productPricingService.changePrice(productId, productPriceChangeDTO);
        return ResponseEntity.ok(productResultDTO);
    }

    @Operation(summary = "Remove product",
            description = "Removes the product. The operation is a soft deletion as the actual entry is not deleted " +
                    "from the database and the product enters in state 'UNAVAILABLE'." +
                    "The operation can be triggered by the following role(s): 'ADMIN'.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class))),
    })
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeProduct(@PathVariable Long productId) {
        productLifecycleService.removeProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
