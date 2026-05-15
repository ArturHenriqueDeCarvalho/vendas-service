package com.artur.vendas.core.usecase;

import com.artur.vendas.core.domain.Order;
import com.artur.vendas.core.exception.OrderNotFoundException;
import com.artur.vendas.core.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateOrderUseCase {

    private final OrderGateway gateway;

    public Order execute(String id, Order updatedData) {
        log.info("[Core] Updating order ID: {}", id);
        
        Order existingOrder = gateway.findById(id)
                .filter(order -> !"CANCELED".equals(order.getStatus()))
                .orElseThrow(() -> new OrderNotFoundException(id));

        existingOrder.setCustomerName(updatedData.getCustomerName());
        existingOrder.setTotalValue(updatedData.getTotalValue());
        
        // Mantemos o status original e ID
        
        Order savedOrder = gateway.save(existingOrder);
        log.info("[Core] Order updated successfully: {}", id);
        return savedOrder;
    }
}
