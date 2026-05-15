package com.artur.vendas.infrastructure.entrypoint;

import com.artur.vendas.core.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Order toDomain(OrderRequest request);

    OrderResponse toResponse(Order order);
}
