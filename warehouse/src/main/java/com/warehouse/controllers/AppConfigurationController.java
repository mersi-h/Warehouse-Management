package com.warehouse.controllers;

import com.warehouse.entities.AppConfiguration;
import com.warehouse.services.AppConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/config")
public class AppConfigurationController {

    private final AppConfigurationService configService;

    @Autowired
    public AppConfigurationController(AppConfigurationService configService) {
        this.configService = configService;
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @GetMapping("/delivery-period")
    public ResponseEntity<Integer> getDeliveryPeriod() {
        return ResponseEntity.ok(configService.getMaxDeliveryPeriod());
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @PutMapping("/delivery-period")
    public ResponseEntity<AppConfiguration> updateDeliveryPeriod(@RequestBody int period) {
        return ResponseEntity.ok(configService.updateMaxDeliveryPeriod(period));
    }
}
