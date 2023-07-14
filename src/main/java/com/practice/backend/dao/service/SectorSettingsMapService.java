package com.practice.backend.dao.service;

import com.practice.backend.dao.mapper.ISectorSettingsMapMapper;
import com.practice.backend.dao.model.SectorSettingsMap;
import com.practice.backend.enums.SectorSettingMapName;
import com.practice.backend.exception.DatabaseException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SectorSettingsMapService extends AbstractServiceWithSectorId<ISectorSettingsMapMapper, SectorSettingsMap> {

    @Autowired
    public SectorSettingsMapService(ISectorSettingsMapMapper mapper) {
        this.mapper = mapper;
    }

    public void updateSettingIfExistOrInsert(@NotNull SectorSettingsMap newSetting) throws DatabaseException {
        List<SectorSettingsMap> settings = getBySectorId(newSetting.getSectorId());

        String settingName = newSetting.getName();

        if (settingName == null) {
            throw new DatabaseException("Name of setting is null!");
        }

        // Поиск настройки с именем settingName
        for (SectorSettingsMap setting : settings) {
            if (settingName.equals(setting.getName())) {
                update(newSetting);
                return;
            }
        }

        // Настройка не найдена, вставляем её в БД
        insert(newSetting);
    }

    @NotNull
    public String getSettingValueWithNameAndSectorId(@NotNull String name, Long sectorId) {
        List<SectorSettingsMap> settings = getBySectorId(sectorId);

        for (SectorSettingsMap setting : settings) {
            if (name.equals(setting.getName())) {
                // Если её значение - true, то показываем почту
                if (setting.getValue() == null) {
                    return "";
                } else {
                    return setting.getValue();
                }
            }
        }

        return "";
    }
}
