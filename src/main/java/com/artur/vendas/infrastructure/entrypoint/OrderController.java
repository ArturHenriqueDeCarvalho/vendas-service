package com.artur.vendas.infrastructure.entrypoint;

import com.artur.vendas.core.domain.Order;
import com.artur.vendas.core.usecase.CreateOrderUseCase;
import com.artur.vendas.core.usecase.FindAllOrdersUseCase;
import com.artur.vendas.core.usecase.FindOrderByIdUseCase;
import com.artur.vendas.core.usecase.InactivateOrderUseCase;
import com.artur.vendas.core.usecase.UpdateOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final FindAllOrdersUseCase findAllOrdersUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final InactivateOrderUseCase inactivateOrderUseCase;
    private final OrderDtoMapper mapper;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        log.info("[Entrypoint] Received request to create order for customer: {}", request.getCustomerName());
        Order order = mapper.toDomain(request);
        Order savedOrder = createOrderUseCase.execute(order);
        OrderResponse response = mapper.toResponse(savedOrder);
        log.info("[Entrypoint] Order created successfully with ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable String id) {
        log.info("[Entrypoint] Received request to find order by ID: {}", id);
        Order order = findOrderByIdUseCase.execute(id);
        return ResponseEntity.ok(mapper.toResponse(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        log.info("[Entrypoint] Received request to find all active orders");
        List<OrderResponse> responses = findAllOrdersUseCase.execute()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable String id, @Valid @RequestBody OrderRequest request) {
        log.info("[Entrypoint] Received request to update order ID: {}", id);
        Order orderToUpdate = mapper.toDomain(request);
        Order updatedOrder = updateOrderUseCase.execute(id, orderToUpdate);
        return ResponseEntity.ok(mapper.toResponse(updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inactivate(@PathVariable String id) {
        log.info("[Entrypoint] Received request to inactivate order ID: {}", id);
        inactivateOrderUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
