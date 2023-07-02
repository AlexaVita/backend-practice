package com.practice.backend.service;

import com.practice.backend.mapper.ISectorMapper;
import com.practice.backend.model.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Наследник AbstractService. Отвечает за конкретную реализацию SectorService */
@Service
public class SectorService extends AbstractService<ISectorMapper, Sector> {

    @Autowired
    SectorService(ISectorMapper sectorMapper) {
        mapper = sectorMapper;
    }

    // TODO сделать удаление
    @Override
    public void delete(Long id) {

    }

    // TODO сделать обновление записи
    @Override
    public void update(Long id, Sector entity) {

    }
}
