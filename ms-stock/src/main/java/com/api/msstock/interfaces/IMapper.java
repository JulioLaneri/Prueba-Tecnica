package com.api.msstock.interfaces;

import com.api.msstock.abstracts.AbstractDto;
import com.api.msstock.abstracts.AbstractEntity;
import com.api.msstock.dtos.ProductDto;
import org.springframework.stereotype.Component;

@Component
public interface IMapper <E extends AbstractEntity, D extends ProductDto> {
    public E toEntity(D dto);
    public D toDto(E entity);
}
