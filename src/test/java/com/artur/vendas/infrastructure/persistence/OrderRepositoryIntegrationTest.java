package com.artur.vendas.infrastructure.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderRepositoryIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve persistir e buscar um OrderEntity no MongoDB com sucesso")
    void shouldSaveAndRetrieveOrderEntity() {
        // Arrange
        OrderEntity entity = new OrderEntity(null, "Maria", 300.0, "CREATED");

        // Act
        OrderEntity savedEntity = orderRepository.save(entity);
        Optional<OrderEntity> retrievedEntity = orderRepository.findById(savedEntity.getId());

        // Assert
        assertTrue(retrievedEntity.isPresent());
        assertEquals("Maria", retrievedEntity.get().getCustomerName());
        assertEquals(300.0, retrievedEntity.get().getTotalValue());
        assertEquals("CREATED", retrievedEntity.get().getStatus());
    }
}
