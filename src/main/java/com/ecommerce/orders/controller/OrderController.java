package com.ecommerce.orders.controller;

import com.ecommerce.orders.dto.OrderRequest;
import com.ecommerce.orders.dto.OrderResponse;
import com.ecommerce.orders.dto.OrderStatusUpdateRequest;
import com.ecommerce.orders.model.OrderStatus;
import com.ecommerce.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order Processing API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new order")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status")
    public OrderResponse updateOrderStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateRequest request) {
        return orderService.updateOrderStatus(id, request.getStatus());
    }

    @GetMapping
    @Operation(summary = "Get all orders, optionally filtered by status")
    public List<OrderResponse> getAllOrders(@RequestParam(required = false) OrderStatus status) {
        return orderService.getAllOrders(status);
    }

    @PostMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Cancel an order (only if PENDING)")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }
}
