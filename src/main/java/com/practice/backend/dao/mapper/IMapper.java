package com.practice.backend.dao.mapper;


import com.practice.backend.dao.model.IGuapEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/** Общий интерфейс для всех Mappers */
@Mapper
public interface IMapper<EntityType extends IGuapEntity> {

    List<EntityType> getAll();

    void insert(EntityType entity);

    EntityType getById(Long id);

    void delete(Long id);

    void update(EntityType entity);

}
