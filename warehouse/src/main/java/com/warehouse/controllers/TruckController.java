package com.warehouse.controllers;

import com.warehouse.dtos.TruckDto;
import com.warehouse.services.ITruckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/trucks")
public class TruckController {

    private final ITruckService iTruckService;

    public TruckController(ITruckService iTruckService) {
        this.iTruckService = iTruckService;
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<TruckDto>> getAllTrucks() {
        return ResponseEntity.status(HttpStatus.OK).body(iTruckService.getAllTrucks());
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @GetMapping(value = "/truck/{id}")
    public ResponseEntity<TruckDto> getTruckById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iTruckService.getTruckById(id));
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @PostMapping(value = "/save")
    public ResponseEntity<TruckDto> saveTruck(@RequestBody TruckDto truckDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iTruckService.saveTruck(truckDto));
    }
    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<TruckDto> updateTruck(@RequestBody TruckDto truckDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iTruckService.updateTruck(truckDto));
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @DeleteMapping(value = "/delete-truck/{id}")
    public void deleteTruck(@PathVariable("id") Long id) {
        iTruckService.deleteTruck(id);
    }



}
