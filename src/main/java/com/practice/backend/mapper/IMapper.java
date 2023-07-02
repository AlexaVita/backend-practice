package com.practice.backend.mapper;


import java.util.List;

/** Общий интерфейс для всех Mappers */
public interface IMapper<EntityType> {

    List<EntityType> getAll();

    void insert(EntityType entity);

    EntityType getById(Long id);


    // TODO сделать удаление и обновление

}
