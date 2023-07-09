package com.practice.backend.dao.service;

import com.practice.backend.dao.mapper.ISectorMapper;
import com.practice.backend.dao.model.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Наследник AbstractService. Отвечает за конкретную реализацию SectorService */
@Service
public class SectorService extends AbstractService<ISectorMapper, Sector> {

    @Autowired
    SectorService(ISectorMapper sectorMapper) {
        mapper = sectorMapper;
    }

}
