package com.warehouse.services;

import com.warehouse.dtos.TruckDto;
import com.warehouse.entities.Truck;

import java.time.LocalDate;
import java.util.List;

public interface ITruckService {

    List<TruckDto> getAllTrucks();

    TruckDto getTruckById(Long id);

    TruckDto saveTruck(TruckDto truckDto);

    TruckDto updateTruck(TruckDto truckDto);

    boolean isTruckUnavailable(Truck truck, LocalDate date);

    void deleteTruck(Long id);
}
