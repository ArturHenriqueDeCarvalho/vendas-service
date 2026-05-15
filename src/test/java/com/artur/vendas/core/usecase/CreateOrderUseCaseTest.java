package com.artur.vendas.core.usecase;

import com.artur.vendas.core.domain.Order;
import com.artur.vendas.core.gateway.OrderGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderGateway gateway;

    @InjectMocks
    private CreateOrderUseCase useCase;

    @Test
    @DisplayName("Deve processar um pedido com sucesso.")
    void shouldExecuteOrderFlowSuccessfully() {
        // Arrange
        Order inputOrder = new Order(null, "Cliente Artur", 250.0, null);
        Order savedOrder = new Order("123", "Cliente Artur", 250.0, "CREATED");

        when(gateway.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order result = useCase.execute(inputOrder);

        // Assert
        assertNotNull(result.getId());
        assertEquals("CREATED", result.getStatus());
        assertEquals("123", result.getId());

        // Verifica se os contratos de Clean Arch foram chamados
        verify(gateway, times(1)).save(any(Order.class));
        verify(gateway, times(1)).notifyCreation(any(Order.class));
        verify(gateway, times(1)).updateStatusCache(anyString(), anyString());
    }
}
