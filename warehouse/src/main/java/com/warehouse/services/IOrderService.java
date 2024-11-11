package com.warehouse.services;

import com.warehouse.dtos.OrderApprovalDeclinedDto;
import com.warehouse.dtos.OrderDetailedDto;
import com.warehouse.dtos.OrderDto;
import com.warehouse.entities.OrderStatus;

import java.util.List;

public interface IOrderService {

    List<OrderDto> getAllOrders(OrderStatus status);

    List<OrderDto> getAllOrdersByUserId(Long userId, OrderStatus status);

    OrderDto getOrderById(Long id);

    OrderDetailedDto getOrderDetailedById(Long id);

    OrderDto saveOrder(OrderDto orderDto);

    OrderDto updateOrder(OrderDto orderDto);

    void deleteOrder(Long id);

    OrderDto approveOrDeclineOrder(OrderApprovalDeclinedDto dto);

}
