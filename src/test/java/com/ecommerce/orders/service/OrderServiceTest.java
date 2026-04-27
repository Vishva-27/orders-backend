package com.ecommerce.orders.service;

import com.ecommerce.orders.dto.OrderItemRequest;
import com.ecommerce.orders.dto.OrderRequest;
import com.ecommerce.orders.dto.OrderResponse;
import com.ecommerce.orders.model.Order;
import com.ecommerce.orders.model.OrderItem;
import com.ecommerce.orders.model.OrderStatus;
import com.ecommerce.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .productId(100L)
                .quantity(2)
                .price(BigDecimal.valueOf(50))
                .build();

        order = Order.builder()
                .id(1L)
                .customerId(10L)
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.valueOf(100))
                .build();
                
        order.addItem(orderItem);
    }

    @Test
    void createOrder_ShouldReturnOrderResponse() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(10L);
        request.setItems(List.of(new OrderItemRequest(100L, 2, BigDecimal.valueOf(50))));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponse response = orderService.createOrder(request);

        assertNotNull(response);
        assertEquals(10L, response.getCustomerId());
        assertEquals(OrderStatus.PENDING, response.getStatus());
        assertEquals(BigDecimal.valueOf(100), response.getTotalAmount());
        assertEquals(1, response.getItems().size());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void getOrderById_ShouldReturnOrderResponse_WhenFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponse response = orderService.getOrderById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(OrderStatus.PENDING, response.getStatus());
    }

    @Test
    void getOrderById_ShouldThrowException_WhenNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void updateOrderStatus_ShouldUpdateStatus() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponse response = orderService.updateOrderStatus(1L, OrderStatus.PROCESSING);

        assertNotNull(response);
        assertEquals(OrderStatus.PROCESSING, response.getStatus());
    }

    @Test
    void cancelOrder_ShouldCancel_WhenPending() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        
        orderService.cancelOrder(1L);
        
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenNotPending() {
        order.setStatus(OrderStatus.PROCESSING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(ResponseStatusException.class, () -> orderService.cancelOrder(1L));
        verify(orderRepository, never()).save(any(Order.class));
    }
}
