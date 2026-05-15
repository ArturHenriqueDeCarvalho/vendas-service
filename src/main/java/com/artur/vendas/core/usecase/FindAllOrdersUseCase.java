package com.artur.vendas.core.usecase;

import com.artur.vendas.core.domain.Order;
import com.artur.vendas.core.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindAllOrdersUseCase {

    private final OrderGateway gateway;

    public List<Order> execute() {
        log.info("[Core] Finding all active orders");
        return gateway.findAllActive();
    }
}
