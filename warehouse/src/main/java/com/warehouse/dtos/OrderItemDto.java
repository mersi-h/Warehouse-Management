package com.warehouse.dtos;

import com.warehouse.entities.InventoryItem;
import com.warehouse.entities.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {

    private Long id;

    private int quantity;

    private double totalPrice;

    private InventoryItem item;

    private Order order;

}
