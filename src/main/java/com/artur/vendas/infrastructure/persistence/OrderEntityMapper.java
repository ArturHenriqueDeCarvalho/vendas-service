package com.artur.vendas.infrastructure.persistence;

import com.artur.vendas.core.domain.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity entity);
}
