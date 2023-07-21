package com.practice.backend.dao.service;

import com.practice.backend.dao.mapper.IFeeMapper;
import com.practice.backend.dao.mapper.IMapperWithSectorId;
import com.practice.backend.dao.model.IGuapEntity;
import com.practice.backend.exception.DatabaseException;

import java.util.List;

public class AbstractServiceWithSectorId<TMapper extends IMapperWithSectorId<EntityType>, EntityType extends IGuapEntity> extends AbstractService<TMapper, EntityType>{

    public List<EntityType> getBySectorId(Long sectorId) {
        return mapper.getBySectorId(sectorId);
    }

}
