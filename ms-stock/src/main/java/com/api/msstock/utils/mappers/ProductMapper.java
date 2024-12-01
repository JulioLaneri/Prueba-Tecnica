package com.api.msstock.utils.mappers;

import com.api.msstock.abstracts.AbstractMapper;
import com.api.msstock.dtos.ProductDto;
import com.api.msstock.entitys.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractMapper<ProductEntity, ProductDto> {
    @Override
    public ProductEntity toEntity(ProductDto dto) {
        return modelMapper.map(dto, ProductEntity.class);
    }

    @Override
    public ProductDto toDto(ProductEntity entity) {
        return modelMapper.map(entity, ProductDto.class);
    }
}
