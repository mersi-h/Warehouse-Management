package com.warehouse.services;


import com.warehouse.dtos.OrderDto;
import com.warehouse.dtos.ScheduleDeliveryDto;

import java.time.LocalDate;
import java.util.List;

public interface IDeliverySchedulerService {

    OrderDto scheduleDelivery(ScheduleDeliveryDto deliveryDto);

    List<LocalDate> getUnavailableDatesForDelivery(Long orderId);
}
