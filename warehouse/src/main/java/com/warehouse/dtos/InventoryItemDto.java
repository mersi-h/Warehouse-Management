package com.warehouse.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class InventoryItemDto {

    private Long id;

    private String itemName;

    private int quantity;

    private double volumePackage;

    private double unitPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Tirane")
    private LocalDateTime lastUpdate;


}
