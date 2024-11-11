package com.warehouse.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TruckDto {

    private Long id;
    private String chassisNumber;
    private String licensePlate;
    private double containerVolume;

}
