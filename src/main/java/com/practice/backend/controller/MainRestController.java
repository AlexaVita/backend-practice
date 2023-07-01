package com.practice.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MainRestController {
    @GetMapping("/ping")
    public String ping(@RequestAttribute("requestUUID") UUID requestUUID) {
        return "pong";
    }
}
