package com.practice.backend.mapper;


import com.practice.backend.model.IGuapEntity;

import java.util.List;

/** Общий интерфейс для всех Mappers */
public interface IMapper<EntityType extends IGuapEntity> {

    List<EntityType> getAll();

    void insert(EntityType entity);

    EntityType getById(Long id);

    void delete(Long id);

    void update(Long updatedId, EntityType entity);

}
