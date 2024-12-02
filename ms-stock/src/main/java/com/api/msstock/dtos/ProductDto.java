package com.api.msstock.dtos;

import com.api.msstock.abstracts.AbstractDto;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;



@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDto extends AbstractDto {
    private String productId;
    private String productName;
    @Min(value = 1, message = "La cantidad debe ser mayor a 1")
    private Integer quantity;

}
