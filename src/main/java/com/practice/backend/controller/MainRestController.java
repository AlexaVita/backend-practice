package com.practice.backend.controller;

import com.practice.backend.repo.ExampleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class MainRestController {
    @Autowired
    ExampleRepo exampleRepo;

    @GetMapping("/ping")
    public String ping(@RequestAttribute("requestUUID") UUID requestUUID) {
        return "pong";
    }

    @GetMapping("/numbers")
    public List<String> getNumbers() {
        try {
            return exampleRepo.getNumbers();
        } catch (SQLException e) {
            e.printStackTrace();
            return Stream.of("SQL exception was thrown").collect(Collectors.toList());
        }
    }
}
