package com.practice.backend.service;

import com.practice.backend.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** CRUD-full сервис с основными методами и их реализациями на generic-types */
public abstract class AbstractService <TMapper extends IMapper<EntityType>, EntityType> {

    protected TMapper mapper;

    public List<EntityType> getAll() {
        return mapper.getAll();
    }

    public EntityType getById(Long id) {
        return mapper.getById(id);
    }


    // TODO сделать удаление
    public void delete(Long id) {
        // здесь удаление
    }

    public void insert(EntityType entity) {
        mapper.insert(entity);
    }


    // TODO сделать обновление
    public void update(Long id, EntityType entity) {
        // здесь обновление
    }
}
