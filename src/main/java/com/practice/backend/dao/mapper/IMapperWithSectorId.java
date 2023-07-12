package com.practice.backend.dao.mapper;

import com.practice.backend.dao.model.IGuapEntity;

import java.util.List;

/** Расширяет интерфейс IMapper, добавляя ещё одну get операцию - getBySectorId
 * Во всех mapper, наследующихся от этого интерфейса необходимо в .xml добавлять
 * операцию getBySectorId:
 *  <select id="getBySectorId">
 *      select * from TABLE where sector_id = (#{sectorId})
 *  </select>*/

public interface IMapperWithSectorId<EntityType extends IGuapEntity> extends IMapper<EntityType> {

    List<EntityType> getBySectorId(Long sectorId);

}
