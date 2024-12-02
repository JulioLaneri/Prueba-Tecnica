package com.api.msventas.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private String productId;
    private String productName;
    @Min(value = 0, message = "La cantidad debe ser mayor o igual que cero")
    private Integer quantity;

}