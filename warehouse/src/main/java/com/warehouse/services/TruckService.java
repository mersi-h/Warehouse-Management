package com.warehouse.services;

import com.warehouse.dtos.OrderItemDto;
import com.warehouse.dtos.TruckDto;
import com.warehouse.entities.OrderItem;
import com.warehouse.entities.Truck;
import com.warehouse.exception.CustomException;
import com.warehouse.repository.TruckRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TruckService implements ITruckService{

    private static final Logger logger = LogManager.getLogger(TruckService.class);

    private final TruckRepository truckRepository;
    private final MappingService mappingService;

    @Autowired
    public TruckService(TruckRepository truckRepository, MappingService mappingService) {
        this.truckRepository = truckRepository;
        this.mappingService = mappingService;
    }

    @Override
    public List<TruckDto> getAllTrucks() {
        logger.info("Getting list of all Trucks");
        return this.truckRepository.findAll().stream().map(truck -> mappingService.mapEntityToDto(truck, TruckDto.class)).collect(Collectors.toList());
    }

    @Override
    public TruckDto getTruckById(Long id) {
        Optional<Truck> truck = truckRepository.findById(id);
        if (truck.isEmpty()) {
            return null;
        }
        logger.info("Getting Truck -> {}", id);
        return mappingService.mapEntityToDto(truck.get(), TruckDto.class);

    }

    @Override
    public TruckDto saveTruck(TruckDto truckDto) {
        if (truckDto == null) {
            throw new CustomException("The item can not be null!");
        }
        if (truckDto.getLicensePlate() == null) {
            throw new CustomException("The License Plate can't be null!");
        }

        logger.info("Saving Truck");

        Truck truck = this.truckRepository.save(mappingService.mapDtoToEntity(truckDto, Truck.class));
        return mappingService.mapEntityToDto(truck, TruckDto.class);
    }

    @Override
    public TruckDto updateTruck(TruckDto truckDto) {
        if (truckDto == null) {
            throw new CustomException("The truck cannot be null!");
        }
        if (truckDto.getId() == null) {
            throw new CustomException("The truck ID cannot be null for updating!");
        }

        Optional<Truck> existingTruckOpt = truckRepository.findById(truckDto.getId());
        if (existingTruckOpt.isEmpty()) {
            throw new CustomException("Truck with ID " + truckDto.getId() + " not found!");
        }

        Truck existingTruck = existingTruckOpt.get();

        existingTruck.setContainerVolume(truckDto.getContainerVolume());
        existingTruck.setLicensePlate(truckDto.getLicensePlate());
        existingTruck.setChassisNumber(truckDto.getChassisNumber());

        Truck updatedItem = truckRepository.save(existingTruck);
        return mappingService.mapEntityToDto(updatedItem, TruckDto.class);
    }

    @Override
    public boolean isTruckUnavailable(Truck truck, LocalDate date) {
        return truck.getUnavailableDates().contains(date) ||
                date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    @Override
    public void deleteTruck(Long id) {
        logger.info("Delete Truck -> {}", id);
        this.truckRepository.deleteById(id);
    }
}
