package com.practice.backend.service;

import com.practice.backend.mapper.ISectorSettingsMapMapper;
import com.practice.backend.model.SectorSettingsMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorSettingsMapService extends AbstractService<ISectorSettingsMapMapper, SectorSettingsMap> {

    @Autowired
    public SectorSettingsMapService(ISectorSettingsMapMapper mapper) {
        this.mapper = mapper;
    }

}
