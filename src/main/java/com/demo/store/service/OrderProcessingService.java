package com.demo.store.service;

import com.demo.store.dto.GenerateOrderDTO;
import com.demo.store.dto.OrderResultDTO;
import com.demo.store.dto.mapper.OrderMapper;
import com.demo.store.exception.custom.BusinessException;
import com.demo.store.exception.custom.ResourceNotFoundException;
import com.demo.store.model.Order;
import com.demo.store.model.Product;
import com.demo.store.model.ProductState;
import com.demo.store.repository.OrderRepository;
import com.demo.store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderProcessingService {
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResultDTO generateOrder(GenerateOrderDTO generateOrderDTO) {
        Product product = getValidProduct(generateOrderDTO);
        Order order = orderMapper.generateOrderDtoToOrder(generateOrderDTO);
        order.setProduct(product);
        BigDecimal cost = computeOrderCost(order.getQuantity(), product);
        order.setCost(cost);
        orderRepository.save(order);
        Integer newQuantity = product.getQuantity() - order.getQuantity();
        product.setQuantity(newQuantity);
        productRepository.save(product);
        return orderMapper.orderToOrderResultDto(order);
    }

    private Product getValidProduct(GenerateOrderDTO generateOrderDTO) {
        Product product = productRepository.findById(generateOrderDTO.getProductId()).orElseThrow( () ->
                new ResourceNotFoundException(String.format("Product with id [%s] could not be found",
                        generateOrderDTO.getProductId())));
        if (!ProductState.AVAILABLE.equals(product.getState())) {
            throw new BusinessException(String.format("Cannot generate order. Reason: Product is in state [%s]",
                    product.getState().name()));
        }
        if (product.getQuantity() < generateOrderDTO.getQuantity()) {
            throw new BusinessException("Cannot generate order. Reason: insufficient quantity.");
        }
        return product;
    }

    private BigDecimal computeOrderCost(Integer quantity, Product product) {
        BigDecimal price = product.getDiscountedPrice() != null ? product.getDiscountedPrice() : product.getPrice();
        return price.multiply(new BigDecimal(quantity));
    }
}
