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
public class FindOrderByIdUseCase {

    private final OrderGateway gateway;

    public Order execute(String id) {
        log.info("[Core] Finding order by ID: {}", id);
        return gateway.findById(id)
                .filter(order -> !"CANCELED".equals(order.getStatus()) && !"INACTIVE".equals(order.getStatus()))
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
