package com.practice.backend.controller;

import com.practice.backend.mapper.ExampleMapper;
import com.practice.backend.model.Example;
import com.practice.backend.model.Sector;
import com.practice.backend.model.SectorSettingsMap;
import com.practice.backend.service.SectorService;
import com.practice.backend.service.SectorSettingsMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class MainRestController {

    @Autowired
    ExampleMapper exampleMapper;

    @Autowired
    SectorService sectorService;

    @Autowired
    SectorSettingsMapService sectorSettingsMapService;

    Logger logger = LoggerFactory.getLogger(MainRestController.class);


    @GetMapping("/ping")
    public String ping(@RequestAttribute("userUUID") UUID userUUID) {
        return "pong";
    }


    @GetMapping("/numbers")
    public List<Example> getNumbers(@RequestAttribute("userUUID") UUID userUUID) {

        return exampleMapper.getAll();
    }

    @GetMapping("/tom")
    public String tom(@RequestAttribute("userUUID") UUID userUUID) {
        logger.info("tom");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "tom";
    }

    @GetMapping("/getAllSectors")
    public List<Sector> getAllSectors() {
        return sectorService.getAll();
    }

    @GetMapping("/insert")
    public void addSector(@RequestParam(name = "name") String name) {
        // Просто тест, нужно переделать на RequestBody
        sectorSettingsMapService.insert(new SectorSettingsMap(4L, 2L, name, "4"));
    }

    @GetMapping("/update")
    public void updateSector() {
        sectorSettingsMapService.update(new SectorSettingsMap(4L, 2L, "GIGIGIGIGIGI", "4"));
    }

    @GetMapping("/delete")
    public void deleteSector() {
        sectorSettingsMapService.delete(2L);
    }

    @GetMapping("/getSectorById")
    public Sector getSectorById(@RequestParam(name = "id") Long id) {
        return sectorService.getById(id);
    }

}
