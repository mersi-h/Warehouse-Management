package com.warehouse.repository;

import com.warehouse.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrder_OrderNumber(Long id);
    List<OrderItem> deleteAllByOrder_OrderNumber(Long id);
}
