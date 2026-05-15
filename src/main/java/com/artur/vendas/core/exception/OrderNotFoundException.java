package com.artur.vendas.core.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String id) {
        super("Order not found or inactive with ID: " + id);
    }
}
