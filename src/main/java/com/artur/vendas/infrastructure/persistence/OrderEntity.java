package com.artur.vendas.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    private String id;
    private String customerName;
    private Double totalValue;
    private String status;
}
