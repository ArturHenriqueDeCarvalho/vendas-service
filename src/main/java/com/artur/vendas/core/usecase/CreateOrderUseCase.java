package com.artur.vendas.core.usecase;

import com.artur.vendas.core.domain.Order;
import com.artur.vendas.core.gateway.OrderGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateOrderUseCase {
    private final OrderGateway gateway;

    public CreateOrderUseCase(OrderGateway gateway) {
        this.gateway = gateway;
    }

    public Order execute(Order order) {
        log.info("[Core] Starting order processing for customer: {}", order.getCustomerName());
        
        order.setStatus("CREATED");
        
        log.info("[Core] Persisting order in database...");
        Order savedOrder = gateway.save(order);
        
        log.info("[Core] Notifying order creation (Kafka)...");
        gateway.notifyCreation(savedOrder);
        
        log.info("[Core] Updating order status in cache (Redis)...");
        gateway.updateStatusCache(savedOrder.getId(), "CREATED");
        
        log.info("[Core] Order processed successfully with ID: {}", savedOrder.getId());
        return savedOrder;
    }
}
