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
class InactivateOrderUseCaseTest {

    @Mock
    private OrderGateway gateway;

    @InjectMocks
    private InactivateOrderUseCase useCase;

    @Test
    @DisplayName("Deve alterar status para CANCELED quando o pedido estiver ativo.")
    void shouldChangeStatusToCanceledWhenOrderIsActive() {
        Order activeOrder = new Order("123", "Cliente", 100.0, "CREATED");
        
        when(gateway.findById("123")).thenReturn(Optional.of(activeOrder));

        useCase.execute("123");

        assertEquals("CANCELED", activeOrder.getStatus());
        verify(gateway, times(1)).save(activeOrder);
        verify(gateway, times(1)).updateStatusCache("123", "CANCELED");
    }

    @Test
    @DisplayName("Deve lançar exceção ao inativar pedido que já está cancelado.")
    void shouldThrowExceptionWhenOrderIsAlreadyCanceled() {
        Order canceledOrder = new Order("123", "Cliente", 100.0, "CANCELED");
        
        when(gateway.findById("123")).thenReturn(Optional.of(canceledOrder));

        assertThrows(OrderNotFoundException.class, () -> useCase.execute("123"));
        verify(gateway, never()).save(any(Order.class));
    }
}
