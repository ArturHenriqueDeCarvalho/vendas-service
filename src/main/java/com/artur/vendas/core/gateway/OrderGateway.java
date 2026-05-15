package com.artur.vendas.core.gateway;

import com.artur.vendas.core.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    Order save(Order order);
    void notifyCreation(Order order);
    void updateStatusCache(String id, String status);
    
    Optional<Order> findById(String id);
    List<Order> findAllActive();
}
