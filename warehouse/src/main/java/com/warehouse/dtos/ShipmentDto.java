package com.warehouse.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.warehouse.entities.Shipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tirane")
    private Date shipmentDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tirane")
    private Date deliveryDate;

    public static ShipmentDto entityToDto(Shipment entity) {
        ShipmentDto dto = new ShipmentDto();
        dto.setId(entity.getId());
        dto.setShipmentDate(Date.from(entity.getShipmentDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dto.setDeliveryDate(Date.from(entity.getDeliveryDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return dto;
    }

    public static Shipment dtoToEntity(ShipmentDto dto) {
        Shipment entity = new Shipment();
        entity.setId(dto.getId());
        entity.setShipmentDate(dto.getShipmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        entity.setDeliveryDate(dto.getDeliveryDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return entity;
    }

}
