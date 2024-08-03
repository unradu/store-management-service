package com.demo.store.api;

import com.demo.store.dto.GenerateOrderDTO;
import com.demo.store.dto.OrderResultDTO;
import com.demo.store.service.OrderProcessingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SALES_ASSOCIATE')")
    public ResponseEntity<OrderResultDTO> generateOrder(@Valid @RequestBody GenerateOrderDTO generateOrderDTO) {
        OrderResultDTO orderResultDTO = orderProcessingService.generateOrder(generateOrderDTO);
        return ResponseEntity.ok(orderResultDTO);
    }
}
