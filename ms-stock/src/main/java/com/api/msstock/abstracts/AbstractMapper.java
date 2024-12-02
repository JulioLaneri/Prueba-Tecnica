package com.api.msstock.abstracts;

import com.api.msstock.dtos.ProductDto;
import com.api.msstock.interfaces.IMapper;
import org.modelmapper.ModelMapper;

public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> implements IMapper<E, D> {
    protected ModelMapper modelMapper = new ModelMapper();
}
