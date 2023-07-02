package com.practice.backend.controller;

import com.practice.backend.mapper.ExampleMapper;
import com.practice.backend.model.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class MainRestController {

    @Autowired
    ExampleMapper exampleMapper;
//    @Autowired
//    ExampleRepo exampleRepo;

    @GetMapping("/ping")
    public String ping(@RequestAttribute("requestUUID") UUID requestUUID) {
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
}
