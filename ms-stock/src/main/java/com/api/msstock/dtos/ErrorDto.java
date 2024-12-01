package com.api.msstock.dtos;

import com.api.msstock.abstracts.AbstractDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private String code;
    private String message;
}
