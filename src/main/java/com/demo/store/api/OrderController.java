package com.demo.store.api;

import com.demo.store.dto.GenerateOrderDTO;
import com.demo.store.dto.OrderResultDTO;
import com.demo.store.exception.ErrorDetails;
import com.demo.store.service.OrderProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderProcessingService orderProcessingService;

    @Operation(summary = "Generate Order",
            description = "Used to generate a new Order for a product. The product must be " +
                    "in status 'AVAILABLE' and have a sufficient quantity. " +
                    "The operation can be triggered by the following role(s): 'ADMIN' and 'SALES_ASSOCIATE'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order generated successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                            useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class))),
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_ASSOCIATE')")
    public ResponseEntity<OrderResultDTO> generateOrder(@Valid @RequestBody GenerateOrderDTO generateOrderDTO) {
        OrderResultDTO orderResultDTO = orderProcessingService.generateOrder(generateOrderDTO);
        return ResponseEntity.ok(orderResultDTO);
    }
}
