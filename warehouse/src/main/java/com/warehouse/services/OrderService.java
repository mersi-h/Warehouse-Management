package com.warehouse.services;

import com.warehouse.dtos.*;
import com.warehouse.entities.*;
import com.warehouse.exception.CustomException;
import com.warehouse.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final MappingService mappingService;
    private final OrderItemRepository orderItemRepository;
    private final TruckRepository truckRepository;
    private final ShipmentRepository shipmentRepository;
    private final InventoryItemRepository itemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MappingService mappingService, OrderItemRepository orderItemRepository, TruckRepository truckRepository, ShipmentRepository shipmentRepository, InventoryItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.mappingService = mappingService;
        this.orderItemRepository = orderItemRepository;
        this.truckRepository = truckRepository;
        this.shipmentRepository = shipmentRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<OrderDto> getAllOrders(OrderStatus status) {
        logger.info("Getting list of all Orders");
        if (status != null) {
            return orderRepository.findAll().stream().filter(o -> o.getStatus().equals(status)).map(OrderDto::entityToDto)
                    .sorted(Comparator.comparing(OrderDto::getSubmittedDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed()).collect(Collectors.toList());
        }
        return this.orderRepository.findAll().stream().map(OrderDto::entityToDto)
                .sorted(Comparator.comparing(OrderDto::getSubmittedDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrdersByUserId(Long userId, OrderStatus status) {
        logger.info("Getting list of all Orders");
        if (status != null) {
            return orderRepository.findAllByUserId(userId).stream().filter(o -> o.getStatus().equals(status)).map(OrderDto::entityToDto)
                    .collect(Collectors.toList());
        }
        logger.info("Getting list of all Orders");
        return this.orderRepository.findAllByUserId(userId).stream().map(OrderDto::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order not found for ID: " + id));
        logger.info("Getting Order -> {}", id);
        return OrderDto.entityToDto(order);
    }

    @Override
    public OrderDetailedDto getOrderDetailedById(Long id) {
        List<OrderItemDto> allOrderItems = orderItemRepository.findAllByOrder_OrderNumber(id).stream()
                .map(o -> mappingService.mapEntityToDto(o, OrderItemDto.class)).collect(Collectors.toList());
        return new OrderDetailedDto(getOrderById(id), allOrderItems);
    }

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        if (orderDto == null) {
            throw new CustomException("The order can not be null!");
        }

        logger.info("Creating new Order");
        Order order = OrderDto.dtoToEntity(orderDto);
        if (orderDto.getStatus().equals(OrderStatus.AWAITING_APPROVAL)) {
            order.setSubmittedDate(LocalDate.now());

        }

        Order orDto = this.orderRepository.save(order);
        return OrderDto.entityToDto(orDto);

    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        if (orderDto == null) {
            throw new CustomException("The Order cannot be null!");
        }
        if (orderDto.getOrderNumber() == null) {
            throw new CustomException("The order number cannot be null for updating!");
        }
        if (!orderDto.getStatus().equals(OrderStatus.CREATED) && !orderDto.getStatus().equals(OrderStatus.DECLINED)) {
            throw new CustomException("The order can only be updated if is in status: CREATED or DECLINED!");
        }

        Optional<Order> existingItemOpt = orderRepository.findById(orderDto.getOrderNumber());
        if (existingItemOpt.isEmpty()) {
            throw new CustomException("Item with Order number " + orderDto.getOrderNumber() + " not found!");
        }

        Order existingOrder = existingItemOpt.get();
        existingOrder.setStatus(orderDto.getStatus());
        existingOrder.setDeadlineDate(orderDto.getDeadlineDate() != null ? orderDto.getDeadlineDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null);
        existingOrder.setTotalPrice(orderItemRepository.findAllByOrder_OrderNumber(orderDto.getOrderNumber()).stream().mapToDouble(OrderItem::getTotalPrice).sum());


        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderDto.entityToDto(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {

        OrderStatus status = this.getOrderById(id).getStatus();
        if (status.equals(OrderStatus.FULFILLED) || status.equals(OrderStatus.UNDER_DELIVERY) || status.equals(OrderStatus.CANCELED)
        ) {
            throw new CustomException("The order can not be canceled in the status" + status.name());
        }

        logger.info("Delete Order-> {}", id);
        if (this.orderItemRepository.findAllByOrder_OrderNumber(id) != null) {
            this.orderItemRepository.deleteAllByOrder_OrderNumber(id);
        }
        this.orderRepository.deleteById(id);
    }

    @Override
    public OrderDto approveOrDeclineOrder(OrderApprovalDeclinedDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new CustomException("Order not found"));

        if (dto.isApprove()) {
            order.setStatus(OrderStatus.APPROVED);
            order.setDeclineReason(null);
        } else {
            order.setStatus(OrderStatus.DECLINED);
            order.setDeclineReason(dto.getDeclineReason());
        }

        return OrderDto.entityToDto(orderRepository.save(order));
    }


}
