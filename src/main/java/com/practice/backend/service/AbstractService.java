package com.practice.backend.service;

import com.practice.backend.mapper.IMapper;
import com.practice.backend.model.IGuapEntity;

import java.util.List;

/** CRUD-full сервис с основными методами и их реализациями на generic-types */
public abstract class AbstractService <TMapper extends IMapper<EntityType>, EntityType extends IGuapEntity> {

    protected TMapper mapper;

    public List<EntityType> getAll() {
        return mapper.getAll();
    }

    public EntityType getById(Long id) {
        return mapper.getById(id);
    }

    public void delete(Long id) {
        mapper.delete(id);
    }

    public void insert(EntityType entity) {
        mapper.insert(entity);
    }

    public void update(Long updatedId, EntityType entity) {
        mapper.update(updatedId, entity);
    }
}
