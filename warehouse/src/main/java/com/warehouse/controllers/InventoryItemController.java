package com.warehouse.controllers;

import com.warehouse.dtos.InventoryItemDto;
import com.warehouse.services.IInventoryItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/items")
public class InventoryItemController {

    private final IInventoryItemService itemService;

    public InventoryItemController(IInventoryItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<InventoryItemDto>> getAllUItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllItems());
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @GetMapping(value = "/item/{id}")
    public ResponseEntity<InventoryItemDto> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemById(id));
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @PostMapping(value = "/save")
    public ResponseEntity<InventoryItemDto> saveItem(@RequestBody InventoryItemDto inventoryItemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItem(inventoryItemDto));
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @PutMapping(value = "/update")
    public ResponseEntity<InventoryItemDto> updateItem(@RequestBody InventoryItemDto inventoryItemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.updateItem(inventoryItemDto));
    }

    @PreAuthorize("hasAuthority('WAREHOUSE_MANAGER')")
    @DeleteMapping(value = "/delete-item/{id}")
    public void deleteUItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
    }

}
