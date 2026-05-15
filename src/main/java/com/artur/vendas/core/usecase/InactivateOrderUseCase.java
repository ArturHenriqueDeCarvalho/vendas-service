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
public class InactivateOrderUseCase {

    private final OrderGateway gateway;

    public void execute(String id) {
        log.info("[Core] Inactivating order ID: {}", id);
        
        Order existingOrder = gateway.findById(id)
                .filter(order -> !"CANCELED".equals(order.getStatus()))
                .orElseThrow(() -> new OrderNotFoundException(id));

        existingOrder.setStatus("CANCELED");
        
        gateway.save(existingOrder);
        
        // Opcional: Atualizar cache e notificar via Kafka
        gateway.updateStatusCache(id, "CANCELED");
        
        log.info("[Core] Order inactivated (Soft Delete) successfully: {}", id);
    }
}
