package com.warehouse.controllers;

import com.warehouse.dtos.OrderApprovalDeclinedDto;
import com.warehouse.dtos.OrderDetailedDto;
import com.warehouse.dtos.OrderDto;
import com.warehouse.entities.OrderStatus;
import com.warehouse.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/orders")
public class OrderController {

    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam(value = "status", required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.getAllOrders(status));
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping(value = "/get-by-user")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@RequestParam("userId") Long userId, @RequestParam(value = "status", required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.getAllOrdersByUserId(userId, status));
    }

    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('WAREHOUSE_MANAGER')")
    @GetMapping(value = "/get-order-detailed/{id}")
    public ResponseEntity<OrderDetailedDto> getOrderDetailedById(@PathVariable("id") Long orderNumber) {
        return ResponseEntity.ok(orderService.getOrderDetailedById(orderNumber));
    }

    @PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_WAREHOUSE_MANAGER')")
    @PostMapping(value = "/save")
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(orderDto));
    }

    @PreAuthorize("hasAuthority('ROLE_CLIENT') or hasAuthority('ROLE_WAREHOUSE_MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.updateOrder(orderDto));
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @DeleteMapping(value = "/delete-order/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @PostMapping(value = "/approve-decline")
    public ResponseEntity<OrderDto> approveOrDeclineOrder(@RequestBody OrderApprovalDeclinedDto deliveryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.approveOrDeclineOrder(deliveryDto));
    }

}
