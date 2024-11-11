package com.warehouse.controllers;

import com.warehouse.dtos.OrderDto;
import com.warehouse.dtos.ScheduleDeliveryDto;
import com.warehouse.services.DeliverySchedulerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/delivery")
public class DeliverySchedulerController {

    private final DeliverySchedulerService deliverySchedulerService;

    public DeliverySchedulerController(DeliverySchedulerService deliverySchedulerService) {
        this.deliverySchedulerService = deliverySchedulerService;
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @PostMapping(value = "/schedule")
    public ResponseEntity<OrderDto> scheduleDelivery(@RequestBody ScheduleDeliveryDto deliveryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(deliverySchedulerService.scheduleDelivery(deliveryDto));
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @GetMapping(value = "/get-dates/{id}")
    public ResponseEntity<List<Date>> getUnavailableDatesForDelivery(@PathVariable("id") Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(deliverySchedulerService.getUnavailableDatesForDelivery(orderId)
                .stream().map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList()));
    }
}
