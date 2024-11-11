package com.warehouse.services;

import com.warehouse.dtos.InventoryItemDto;
import com.warehouse.entities.InventoryItem;
import com.warehouse.exception.CustomException;
import com.warehouse.repository.InventoryItemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryItemService implements IInventoryItemService {
    private static final Logger logger = LogManager.getLogger(InventoryItemService.class);


    private final InventoryItemRepository inventoryItemRepository;
    private final MappingService mappingService;

    @Autowired
    public InventoryItemService(InventoryItemRepository inventoryItemRepository, MappingService mappingService) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.mappingService = mappingService;
    }

    @Override
    public List<InventoryItemDto> getAllItems() {
        logger.info("Getting list of all Inventory items");
        return this.inventoryItemRepository.findAll().stream().map(item -> mappingService.mapEntityToDto(item, InventoryItemDto.class)).collect(Collectors.toList());
    }

    @Override
    public InventoryItemDto getItemById(Long id) {
        Optional<InventoryItem> item = inventoryItemRepository.findById(id);
        if (item.isEmpty()) {
            return null;
        }
        logger.info("Getting Inventory Item -> {}", id);
        return mappingService.mapEntityToDto(item.get(), InventoryItemDto.class);

    }

    @Override
    public InventoryItemDto addItem(InventoryItemDto itemDto) {
        if (itemDto == null) {
            throw new CustomException("The item can not be null!");
        }
        if (itemDto.getItemName() == null) {
            throw new CustomException("The item name can't be null!");
        }
        logger.info("Adding new Inventory Item");
        itemDto.setLastUpdate(LocalDateTime.now());
        InventoryItem item = this.inventoryItemRepository.save(mappingService.mapDtoToEntity(itemDto, InventoryItem.class));
        return mappingService.mapEntityToDto(item, InventoryItemDto.class);
    }

    @Override
    public InventoryItemDto updateItem(InventoryItemDto itemDto) {

            if (itemDto == null) {
                throw new CustomException("The item cannot be null!");
            }
            if (itemDto.getId() == null) {
                throw new CustomException("The item ID cannot be null for updating!");
            }

            Optional<InventoryItem> existingItemOpt = inventoryItemRepository.findById(itemDto.getId());
            if (existingItemOpt.isEmpty()) {
                throw new CustomException("Item with ID " + itemDto.getId() + " not found!");
            }

            InventoryItem existingItem = existingItemOpt.get();

            existingItem.setItemName(itemDto.getItemName());
            existingItem.setQuantity(itemDto.getQuantity());
            existingItem.setUnitPrice(itemDto.getUnitPrice());
            existingItem.setVolumePackage(itemDto.getVolumePackage());
            existingItem.setLastUpdate(LocalDateTime.now());

            InventoryItem updatedItem = inventoryItemRepository.save(existingItem);
            return mappingService.mapEntityToDto(updatedItem, InventoryItemDto.class);
    }

    @Override
    public void deleteItem(Long id) {
        logger.info("Delete Inventory Item -> {}", id);
        this.inventoryItemRepository.deleteById(id);
    }
}
