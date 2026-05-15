package com.artur.vendas.core.gateway;

import com.artur.vendas.core.domain.Order;

public interface OrderGateway {
    Order save(Order order);
    void notifyCreation(Order order);
    void updateStatusCache(String id, String status);
}
