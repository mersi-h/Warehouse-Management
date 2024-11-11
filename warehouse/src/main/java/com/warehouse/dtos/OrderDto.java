package com.warehouse.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.warehouse.entities.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private Long orderNumber;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tirane")
    private Date submittedDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tirane")
    private Date deadlineDate;

    private double totalPrice;

    private OrderStatus status;

    private User user;

    private Shipment shipment;

    private String declineReason;

    public static OrderDto entityToDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setSubmittedDate(entity.getSubmittedDate() != null ? Date.from(entity.getSubmittedDate().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
        dto.setDeadlineDate(entity.getDeadlineDate() != null ? Date.from(entity.getDeadlineDate().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setDeclineReason(entity.getDeclineReason());
        dto.setStatus(entity.getStatus());
        dto.setUser(entity.getUser());
        dto.setShipment(entity.getShipment());
        return dto;
    }

    public static Order dtoToEntity(OrderDto dto) {
        Order entity = new Order();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setSubmittedDate(dto.getSubmittedDate() != null ? dto.getSubmittedDate().toInstant().atZone(ZoneId
                .systemDefault()).toLocalDate() : null);
        entity.setDeadlineDate(dto.getDeadlineDate() != null ? dto.getDeadlineDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : null);
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setDeclineReason(dto.getDeclineReason());
        entity.setStatus(dto.getStatus());
        entity.setUser(dto.getUser());
        entity.setShipment(dto.getShipment());
        return entity;
    }
}
