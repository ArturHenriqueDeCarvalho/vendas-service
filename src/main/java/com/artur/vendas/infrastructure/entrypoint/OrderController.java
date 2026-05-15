package com.artur.vendas.infrastructure.entrypoint;

import com.artur.vendas.core.domain.Order;
import com.artur.vendas.core.usecase.CreateOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final OrderDtoMapper mapper;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        log.info("[Entrypoint] Received request to create order for customer: {}", request.getCustomerName());

        // Map DTO to Domain using MapStruct
        Order order = mapper.toDomain(request);

        // Execute Use Case
        Order savedOrder = createOrderUseCase.execute(order);

        // Map Domain to Response DTO using MapStruct
        OrderResponse response = mapper.toResponse(savedOrder);

        log.info("[Entrypoint] Order created successfully with ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
