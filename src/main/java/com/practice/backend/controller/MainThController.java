package com.practice.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class MainThController {

    /** Запрос '/api/hello' возвращает hello_world.html с дефолтным текстом "Hello World!",
     *  если изменить запрос на '/api/hello?name=MyName', то получим изменённую страницу по шаблону,
     *  которая теперь будет выводить "Hello MyName!" */
    @GetMapping("/hello")
    public String hello(@RequestAttribute(name = "requestUUID") UUID requestUUID, @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello_world";
    }

}
