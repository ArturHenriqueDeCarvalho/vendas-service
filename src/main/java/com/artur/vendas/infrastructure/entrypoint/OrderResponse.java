package com.artur.vendas.infrastructure.entrypoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private String customerName;
    private Double totalValue;
    private String status;
}
