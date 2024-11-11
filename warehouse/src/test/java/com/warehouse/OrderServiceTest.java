package com.warehouse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.warehouse.dtos.OrderDto;
import com.warehouse.entities.OrderStatus;
import com.warehouse.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.warehouse.entities.Order;
import com.warehouse.repository.OrderRepository;
import com.warehouse.services.OrderService;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testGetOrderById_Found() {
        Long orderId = 10L;
        Order order = new Order();
        order.setOrderNumber(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> mockReturn = orderRepository.findById(orderId);
        System.out.println("Mock return: " + mockReturn);

        OrderDto result = orderService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getOrderNumber());
    }


    @Test
    void testGetOrderById_NotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> orderService.getOrderById(orderId));
    }

    @Test
    void testSaveOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber(1L);
        orderDto.setStatus(OrderStatus.AWAITING_APPROVAL);

        Order orderEntity = new Order();
        orderEntity.setOrderNumber(1L);
        orderEntity.setStatus(OrderStatus.AWAITING_APPROVAL);

        when(orderRepository.save(any(Order.class))).thenReturn(orderEntity);

        orderService.saveOrder(orderDto);

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
