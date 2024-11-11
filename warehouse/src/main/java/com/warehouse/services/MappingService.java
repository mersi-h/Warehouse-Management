package com.warehouse.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    private final ModelMapper modelMapper = new ModelMapper();

    public <D, T> D mapEntityToDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <T, D> T mapDtoToEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
