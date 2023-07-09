package com.practice.backend.dao.service;

import com.practice.backend.dao.model.IGuapEntity;
import com.practice.backend.exception.DatabaseException;
import com.practice.backend.dao.mapper.IMapper;

import java.util.List;

/** CRUD-full сервис с основными методами и их реализациями на generic-types */
public abstract class AbstractService <TMapper extends IMapper<EntityType>, EntityType extends IGuapEntity> {

    protected TMapper mapper;

    public List<EntityType> getAll() throws DatabaseException {
        List<EntityType> entities = mapper.getAll();
        if (entities == null) {
            throw new DatabaseException("No entities in such table");
        }
        return entities;
    }

    public EntityType getById(Long id) throws DatabaseException {
        EntityType entity = mapper.getById(id);
        if (entity == null) {
            throw new DatabaseException("No entity with such id in table");
        }
        return entity;
    }

    public void delete(Long id) {
        mapper.delete(id);
    }

    public void insert(EntityType entity) {
        mapper.insert(entity);
    }

    public void update(EntityType entity) {
        mapper.update(entity);
    }
}
