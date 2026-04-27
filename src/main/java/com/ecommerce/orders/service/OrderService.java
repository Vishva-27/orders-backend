package com.ecommerce.orders.service;

import com.ecommerce.orders.dto.OrderRequest;
import com.ecommerce.orders.dto.OrderResponse;
import com.ecommerce.orders.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrderById(Long id);
    OrderResponse updateOrderStatus(Long id, OrderStatus status);
    List<OrderResponse> getAllOrders(OrderStatus status);
    void cancelOrder(Long id);
}
