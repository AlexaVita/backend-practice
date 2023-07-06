package com.practice.backend.controller;

import com.practice.backend.exception.PaymentException;
import com.practice.backend.model.Sector;
import com.practice.backend.service.SectorService;
import com.practice.backend.util.PaymentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class MainThController {
    @Autowired
    PaymentUtil paymentUtil;
    @Autowired
    SectorService sectorService;

    Logger logger = LoggerFactory.getLogger(MainThController.class);

    /** Запрос '/api/hello' возвращает hello_world.html с дефолтным текстом "Hello World!",
     *  если изменить запрос на '/api/hello?name=MyName', то получим изменённую страницу по шаблону,
     *  которая теперь будет выводить "Hello MyName!" */
    @GetMapping("/hello")
    public String hello(@RequestAttribute(name = "userUUID") UUID userUUID, @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello_world";
    }

    @GetMapping("/Payment")
    public String getPayment(@RequestAttribute(name = "userUUID") UUID userUUID, HttpServletRequest requestBody, Model model) {



        Long sec= Long.valueOf(requestBody.getParameter("sec"));

        logger.info(String.valueOf(sec));
        Sector sector = sectorService.getById(sec);
        logger.info(sector.getName());

        try {
            paymentUtil.checkIp(requestBody.getRemoteAddr(), userUUID, sector);
        } catch (PaymentException e) {
            logger.info(e.getMessage());
        }

        model.addAttribute("sec", sec);


        return "payment";
    }

}
