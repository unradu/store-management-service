package com.demo.store.dto.mapper;

import com.demo.store.dto.GenerateOrderDTO;
import com.demo.store.dto.OrderResultDTO;
import com.demo.store.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order generateOrderDtoToOrder(GenerateOrderDTO dto);

    @Mapping(source = "product.id", target = "productId")
    OrderResultDTO orderToOrderResultDto(Order order);
}
