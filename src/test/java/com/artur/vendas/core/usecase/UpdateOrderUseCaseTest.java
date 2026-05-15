package com.artur.vendas.core.usecase;

import com.artur.vendas.core.domain.Order;
import com.artur.vendas.core.exception.OrderNotFoundException;
import com.artur.vendas.core.gateway.OrderGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOrderUseCaseTest {

    @Mock
    private OrderGateway gateway;

    @InjectMocks
    private UpdateOrderUseCase useCase;

    @Test
    @DisplayName("Deve atualizar os campos permitidos de um pedido ativo.")
    void shouldUpdateAllowedFieldsOfActiveOrder() {
        Order existingOrder = new Order("123", "Old Name", 100.0, "CREATED");
        Order updateData = new Order(null, "New Name", 200.0, null);
        Order savedOrder = new Order("123", "New Name", 200.0, "CREATED");

        when(gateway.findById("123")).thenReturn(Optional.of(existingOrder));
        when(gateway.save(any(Order.class))).thenReturn(savedOrder);

        Order result = useCase.execute("123", updateData);

        assertEquals("New Name", result.getCustomerName());
        assertEquals(200.0, result.getTotalValue());
        assertEquals("CREATED", result.getStatus());
        verify(gateway, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar pedido cancelado.")
    void shouldThrowExceptionWhenUpdatingCanceledOrder() {
        Order canceledOrder = new Order("123", "Old Name", 100.0, "CANCELED");
        Order updateData = new Order(null, "New Name", 200.0, null);

        when(gateway.findById("123")).thenReturn(Optional.of(canceledOrder));

        assertThrows(OrderNotFoundException.class, () -> useCase.execute("123", updateData));
        verify(gateway, never()).save(any(Order.class));
    }
}
