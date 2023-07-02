package com.practice.backend.controller;

import com.practice.backend.mapper.ExampleMapper;
import com.practice.backend.mapper.ISectorMapper;
import com.practice.backend.model.Example;
import com.practice.backend.model.Sector;
import com.practice.backend.service.SectorService;
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

//    @Autowired
//    ExampleRepo exampleRepo;

    @GetMapping("/ping")
    public String ping(@RequestAttribute("userUUID") UUID userUUID) {
        return "pong";
    }

//    @GetMapping("/numbers")
//    public List<String> getNumbers() {
//        try {
//            return exampleRepo.getNumbers();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return Stream.of("SQL exception was thrown").collect(Collectors.toList());
//        }
//    }

    @GetMapping("/numbers")
    public List<Example> getNumbers() {
        return exampleMapper.getAll();
    }

    @GetMapping("/getAllSectors")
    public List<Sector> getAllSectors(){return sectorService.getAll();}

    @GetMapping("/insert")
    public void addSector(@RequestParam(name = "name") String name) {
        // Просто тест, нужно переделать на RequestBody
        sectorService.insert(new Sector(3L,name,true,"signCode",false,"allowedIps"));
    }

    @GetMapping("/getSectorById")
    public Sector getSectorById(@RequestParam(name = "id") Long id){return sectorService.getById(id);}

}
