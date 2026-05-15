package com.artur.vendas.infrastructure.entrypoint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    @NotNull(message = "Total value is required")
    @Positive(message = "Total value must be positive")
    private Double totalValue;
}
