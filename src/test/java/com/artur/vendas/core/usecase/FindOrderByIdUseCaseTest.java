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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindOrderByIdUseCaseTest {

    @Mock
    private OrderGateway gateway;

    @InjectMocks
    private FindOrderByIdUseCase useCase;

    @Test
    @DisplayName("Deve retornar o pedido com sucesso se existir e estiver ativo.")
    void shouldReturnOrderWhenExistsAndActive() {
        Order activeOrder = new Order("123", "Cliente", 100.0, "CREATED");
        when(gateway.findById("123")).thenReturn(Optional.of(activeOrder));

        Order result = useCase.execute("123");

        assertEquals("123", result.getId());
        assertEquals("CREATED", result.getStatus());
    }

    @Test
    @DisplayName("Deve lançar OrderNotFoundException se o pedido estiver cancelado.")
    void shouldThrowExceptionWhenOrderIsCanceled() {
        Order canceledOrder = new Order("123", "Cliente", 100.0, "CANCELED");
        when(gateway.findById("123")).thenReturn(Optional.of(canceledOrder));

        assertThrows(OrderNotFoundException.class, () -> useCase.execute("123"));
    }

    @Test
    @DisplayName("Deve lançar OrderNotFoundException se o pedido não existir.")
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        when(gateway.findById("999")).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> useCase.execute("999"));
    }
}
