package com.warehouse.services;

import com.warehouse.entities.Order;
import com.warehouse.entities.OrderStatus;
import com.warehouse.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduledDeliveryStatus {

    private final OrderRepository orderRepository;

    @Autowired
    public ScheduledDeliveryStatus(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void checkAndUpdateDeliveries() {
        LocalDate today = LocalDate.now();
        List<Order> dueDeliveries = orderRepository.findAllByShipment_ShipmentDate(today);

        dueDeliveries.forEach(delivery -> {
            if (!"FULFILLED".equals(delivery.getStatus())) {
                delivery.setStatus(OrderStatus.FULFILLED);
            }
        });

        orderRepository.saveAll(dueDeliveries);
    }
}
