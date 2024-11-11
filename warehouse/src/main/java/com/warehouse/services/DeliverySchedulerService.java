package com.warehouse.services;

import com.warehouse.dtos.OrderDto;
import com.warehouse.dtos.ScheduleDeliveryDto;
import com.warehouse.entities.*;
import com.warehouse.exception.CustomException;
import com.warehouse.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliverySchedulerService implements IDeliverySchedulerService {

    private final ShipmentRepository shipmentRepository;
    private final TruckRepository truckRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final InventoryItemRepository itemRepository;
    private final TruckService truckService;
    private final AppConfigurationService appConfigurationService;


    public DeliverySchedulerService(ShipmentRepository shipmentRepository, TruckRepository truckRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository, InventoryItemRepository itemRepository, TruckService truckService, AppConfigurationService appConfigurationService) {
        this.shipmentRepository = shipmentRepository;
        this.truckRepository = truckRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.truckService = truckService;
        this.appConfigurationService = appConfigurationService;
    }

    @Override
    @Transactional
    public OrderDto scheduleDelivery(ScheduleDeliveryDto deliveryDto) {
        Order order = orderRepository.findById(deliveryDto.getOrderId())
                .orElseThrow(() -> new CustomException("Order not found"));

        if (order.getStatus() != OrderStatus.APPROVED) {
            throw new CustomException("Order is not approved for delivery");
        }

        List<Truck> selectedTrucks = truckRepository.findAllById(deliveryDto.getTruckIds());
        double totalTruckCapacity = calculateTotalCapacity(selectedTrucks, deliveryDto.getScheduledDate());
        double orderCapacity = calculateOrderCapacity(order);

        if (orderCapacity > totalTruckCapacity) {
            throw new CustomException("Order capacity exceeds available truck capacity.");
        }

        order.setStatus(OrderStatus.UNDER_DELIVERY);
        updateInventoryItems(order);

        Shipment shipment = createShipment(deliveryDto.getScheduledDate());
        order.setShipment(shipment);
        orderRepository.save(order);

        markTrucksUnavailable(selectedTrucks, deliveryDto.getScheduledDate());

        return OrderDto.entityToDto(order);
    }

    private double calculateOrderCapacity(Order order) {
        return orderItemRepository.findAllByOrder_OrderNumber(order.getOrderNumber())
                .stream()
                .mapToDouble(orderItem -> orderItem.getItem().getVolumePackage() * orderItem.getQuantity())
                .sum();
    }
    private double calculateTotalCapacity(List<Truck> trucks, LocalDate date) {
        double capacity = 0;
        for (Truck truck : trucks) {
            if (truckService.isTruckUnavailable(truck, date)) {
                throw new CustomException("Truck is unavailable on the selected date");
            }
            capacity += truck.getContainerVolume();
        }
        return capacity;
    }

    private void updateInventoryItems(Order order) {
        orderItemRepository.findAllByOrder_OrderNumber(order.getOrderNumber()).forEach(orderItem -> {
            InventoryItem item = itemRepository.findById(orderItem.getItem().getId()).orElseThrow();
            item.setQuantity(item.getQuantity() - orderItem.getQuantity());
            item.setLastUpdate(LocalDateTime.now());
            itemRepository.save(item);
        });
    }

    private Shipment createShipment(LocalDate scheduledDate) {
        Shipment shipment = new Shipment();
        shipment.setShipmentDate(scheduledDate);
        shipment.setDeliveryDate(scheduledDate);
        return shipmentRepository.save(shipment);
    }

    private void markTrucksUnavailable(List<Truck> trucks, LocalDate date) {
        trucks.forEach(truck -> {
            truck.getUnavailableDates().add(date);
        });
        truckRepository.saveAll(trucks);
    }

    @Override
    public List<LocalDate> getUnavailableDatesForDelivery(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found"));

        if (order.getStatus() != OrderStatus.APPROVED) {
            throw new CustomException("Order is not approved for delivery");
        }

        int period = this.appConfigurationService.getMaxDeliveryPeriod();

        period = Math.min(period, 30);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(period);

        List<Truck> requiredTrucks = getRequiredTrucksForOrder(order);


        return startDate.datesUntil(endDate)
                .filter(date -> isWeekend(date) || !areTrucksAvailable(requiredTrucks, date))
                .collect(Collectors.toList());
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean areTrucksAvailable(List<Truck> trucks, LocalDate date) {
        return trucks.stream().noneMatch(truck -> truck.getUnavailableDates().contains(date));
    }

    private List<Truck> getRequiredTrucksForOrder(Order order) {
        double orderCapacity = calculateOrderCapacity(order);
        List<Truck> availableTrucks = truckRepository.findAll();

        List<Truck> requiredTrucks = new ArrayList<>();
        double accumulatedCapacity = 0;

        for (Truck truck : availableTrucks) {
            if (accumulatedCapacity >= orderCapacity) break;
            requiredTrucks.add(truck);
            accumulatedCapacity += truck.getContainerVolume();
        }

        if (accumulatedCapacity < orderCapacity) {
            throw new CustomException("Insufficient truck capacity to fulfill this order");
        }

        return requiredTrucks;
    }




}
