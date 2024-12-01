package com.api.msventas.dtos;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private String productId;
    private String productName;
    @Min(value = 0, message = "La cantidad debe ser mayor o igual que cero")
    private Integer quantity;

}