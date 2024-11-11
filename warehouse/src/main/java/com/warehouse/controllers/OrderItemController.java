package com.warehouse.controllers;

import com.warehouse.dtos.InventoryItemDto;
import com.warehouse.dtos.OrderItemDto;
import com.warehouse.services.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/order-items")
public class OrderItemController {
    private final IOrderItemService orderItemService;

    @Autowired
    public OrderItemController(IOrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }
    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<OrderItemDto>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }
    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping(value = "/order-item/{id}")
    public ResponseEntity<OrderItemDto> getOrderItemById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderItemService.getOrderItemById(id));
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping(value = "/get-by-order/{order-id}")
    public ResponseEntity<List<OrderItemDto>> getOrderItemsByOrderId(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderItemService.getOrderItemsByOrderId(orderId));
    }
    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping(value = "/save")
    public ResponseEntity<OrderItemDto> saveOrderItem(@RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.addOrderItem(orderItemDto));
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping(value = "/update")
    public ResponseEntity<OrderItemDto> updateOrderItem(@RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.updateOrderItem(orderItemDto));
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @DeleteMapping(value = "/{id}")
    public void deleteOrderItem(@PathVariable("id") Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
