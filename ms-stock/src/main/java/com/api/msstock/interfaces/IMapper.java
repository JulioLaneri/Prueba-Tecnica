package com.api.msstock.interfaces;

import com.api.msstock.abstracts.AbstractDto;
import com.api.msstock.abstracts.AbstractEntity;
import org.springframework.stereotype.Component;

@Component
public interface IMapper <E extends AbstractEntity, D extends AbstractDto> {
    public E toEntity(D dto);
    public D toDto(E entity);
}
