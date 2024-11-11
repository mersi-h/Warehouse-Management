package com.warehouse.services;

import com.warehouse.dtos.InventoryItemDto;
import java.util.List;

public interface IInventoryItemService {

    List<InventoryItemDto> getAllItems();

    InventoryItemDto getItemById(Long id);

    InventoryItemDto addItem(InventoryItemDto itemDto);

    InventoryItemDto updateItem(InventoryItemDto itemDto);

    void deleteItem(Long id);
}
