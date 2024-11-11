package com.warehouse.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDeliveryDto {

    private Long orderId;
    private LocalDate scheduledDate;
    private List<Long> truckIds;

}
