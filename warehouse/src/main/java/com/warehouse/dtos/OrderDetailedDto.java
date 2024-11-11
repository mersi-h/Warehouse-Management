package com.warehouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailedDto {

    private OrderDto orderDto;
    private List<OrderItemDto> orderItemDtos;
}
