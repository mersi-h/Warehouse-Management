package com.warehouse.services;

import com.warehouse.dtos.OrderItemDto;

import java.util.List;

public interface IOrderItemService {

    List<OrderItemDto> getAllOrderItems();

    OrderItemDto getOrderItemById(Long id);

    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    OrderItemDto addOrderItem(OrderItemDto itemDto);

    OrderItemDto updateOrderItem(OrderItemDto itemDto);

    void deleteOrderItem(Long id);
}
