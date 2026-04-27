package com.ecommerce.orders.job;

import com.ecommerce.orders.model.Order;
import com.ecommerce.orders.model.OrderStatus;
import com.ecommerce.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProcessingJob {

    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 300000) // 5 minutes in milliseconds
    @Transactional
    public void processPendingOrders() {
        log.info("Starting order processing job to update PENDING orders");
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);
        
        if (pendingOrders.isEmpty()) {
            log.info("No pending orders found");
            return;
        }

        for (Order order : pendingOrders) {
            order.setStatus(OrderStatus.PROCESSING);
            log.info("Updated order {} to PROCESSING", order.getId());
        }
        
        orderRepository.saveAll(pendingOrders);
        log.info("Finished order processing job. Processed {} orders", pendingOrders.size());
    }
}
