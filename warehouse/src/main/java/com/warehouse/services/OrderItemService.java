package com.warehouse.services;

import com.warehouse.dtos.InventoryItemDto;
import com.warehouse.dtos.OrderDto;
import com.warehouse.dtos.OrderItemDto;
import com.warehouse.entities.InventoryItem;
import com.warehouse.entities.Order;
import com.warehouse.entities.OrderItem;
import com.warehouse.exception.CustomException;
import com.warehouse.repository.OrderItemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService implements IOrderItemService{

    private static final Logger logger = LogManager.getLogger(OrderItemService.class);


    private final OrderItemRepository orderItemRepository;
    private final MappingService mappingService;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, MappingService mappingService) {
        this.orderItemRepository = orderItemRepository;
        this.mappingService = mappingService;
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        logger.info("Getting list of all Order Item");
        return this.orderItemRepository.findAll().stream().map(item -> mappingService
                .mapEntityToDto(item, OrderItemDto.class)).collect(Collectors.toList());

    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        Optional<OrderItem> order = orderItemRepository.findById(id);
        if (order.isEmpty()) {
            return null;
        }
        logger.info("Getting Order -> {}", id);
        return mappingService.mapEntityToDto(order.get(), OrderItemDto.class);
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orders = orderItemRepository.findAllByOrder_OrderNumber(orderId);
        logger.info("Getting list of all Order Item By Order Id");

        return orders.stream().map(item -> mappingService
                .mapEntityToDto(item, OrderItemDto.class)).collect(Collectors.toList());
    }
    @Override
    public OrderItemDto addOrderItem(OrderItemDto itemDto) {
        if (itemDto == null) {
            throw new CustomException("The order can not be null!");
        }
        if (itemDto.getOrder() == null) {
            throw new CustomException("The order can not be null!");
        }
        if (itemDto.getItem() == null) {
            throw new CustomException("The item  can't be null!");
        }
        logger.info("Adding Order");
        itemDto.setTotalPrice(itemDto.getQuantity() * itemDto.getItem().getUnitPrice());

        OrderItem item = this.orderItemRepository.save(mappingService.mapDtoToEntity(itemDto, OrderItem.class));
        return mappingService.mapEntityToDto(item, OrderItemDto.class);
    }


    @Override
    public OrderItemDto updateOrderItem(OrderItemDto itemDto) {
        if (itemDto == null) {
            throw new CustomException("The item cannot be null!");
        }
        if (itemDto.getId() == null) {
            throw new CustomException("The item ID cannot be null for updating!");
        }

        Optional<OrderItem> existingItemOpt = orderItemRepository.findById(itemDto.getId());
        if (existingItemOpt.isEmpty()) {
            throw new CustomException("Item with ID " + itemDto.getId() + " not found!");
        }

        OrderItem existingItem = existingItemOpt.get();

        existingItem.setQuantity(itemDto.getQuantity());
        existingItem.setItem(itemDto.getItem());
        existingItem.setTotalPrice(itemDto.getQuantity() * itemDto.getItem().getUnitPrice());
        existingItem.setOrder(itemDto.getOrder());

        OrderItem updatedItem = orderItemRepository.save(existingItem);
        return mappingService.mapEntityToDto(updatedItem, OrderItemDto.class);
    }

    @Override
    public void deleteOrderItem(Long id) {
        logger.info("Delete Order-> {}", id);
        this.orderItemRepository.deleteById(id);
    }
}
