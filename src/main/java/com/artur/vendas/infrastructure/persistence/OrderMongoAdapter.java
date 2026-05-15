package com.artur.vendas.infrastructure.persistence;

import com.artur.vendas.core.domain.Order;
import com.artur.vendas.core.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMongoAdapter implements OrderGateway {

    private final OrderRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final StringRedisTemplate redisTemplate;
    private final OrderEntityMapper mapper;

    @Override
    public Order save(Order order) {
        // Domain -> Entity using MapStruct
        OrderEntity entity = mapper.toEntity(order);

        log.info("[Infrastructure] Saving order entity to MongoDB...");
        OrderEntity savedEntity = repository.save(entity);

        // Entity -> Domain using MapStruct
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void notifyCreation(Order order) {
        log.info("[Infrastructure] Sending message to Kafka topic 'vendas-topic' for Order ID: {}", order.getId());
        kafkaTemplate.send("vendas-topic", order.getId());
    }

    @Override
    public void updateStatusCache(String id, String status) {
        log.info("[Infrastructure] Saving status '{}' to Redis for Order ID: {}", status, id);
        redisTemplate.opsForValue().set("order:status:" + id, status, Duration.ofMinutes(5));
    }

    @Override
    public java.util.Optional<Order> findById(String id) {
        log.info("[Infrastructure] Fetching order from MongoDB by ID: {}", id);
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public java.util.List<Order> findAllActive() {
        log.info("[Infrastructure] Fetching all active orders from MongoDB...");
        return repository.findByStatusNot("CANCELED")
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
