package com.practice.backend.controller;

import com.practice.backend.exception.PaymentException;
import com.practice.backend.logging.Logging;
import com.practice.backend.model.Sector;
import com.practice.backend.service.CheckService;
import com.practice.backend.service.SectorService;
import com.practice.backend.util.PaymentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class MainThController {
    @Autowired
    CheckService checkService;
    @Autowired
    SectorService sectorService;

    /**
     * Запрос '/api/hello' возвращает hello_world.html с дефолтным текстом "Hello World!",
     * если изменить запрос на '/api/hello?name=MyName', то получим изменённую страницу по шаблону,
     * которая теперь будет выводить "Hello MyName!"
     */
    @GetMapping("/hello")
    public String hello(@RequestAttribute(name = "userUUID") UUID userUUID, @RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello_world";
    }

    @GetMapping("/Payment")
    public String getPayment(@RequestAttribute(name = "userUUID") UUID userUUID, HttpServletRequest requestBody, Model model) {
        // Получаем основные параметры из запроса
        Long sectorId = Long.valueOf(requestBody.getParameter("sectorId"));
        Double amount = Double.valueOf(Integer.valueOf(requestBody.getParameter("amount")));
        String desc = requestBody.getParameter("desc");


        Sector sector = sectorService.getById(sectorId);

        // Если не экшн-пэй
        if (requestBody.getParameter("action") == null || !requestBody.getParameter("action").equals("pay")) {
            //logger.info(requestBody.getParameter("action"));


            try {
                // Проверяем сектор на активность -> Проверяем Ip -> Высчитываем сигнатуру
                List<String> paymentParamsNames = Arrays.asList("sectorId", "amount", "desc");
                String encodedSignature = checkService.checkIpAndSectorActiveAndGetEncodedSignature(requestBody, userUUID, sector, paymentParamsNames);

                // Заполняем модель, в т.ч. закидываем высчитанную сигнатуру
                model.addAttribute("sectorId", sectorId);
                model.addAttribute("amount", amount);
                model.addAttribute("desc", desc);

                model.addAttribute("name", sector.getName());
                model.addAttribute("signature", encodedSignature);

            } catch (PaymentException e) {
                Logging.info("getPayment(userUUID = " + userUUID + ')', e.getMessage());
            }
        } else {
            Logging.info("getPayment(userUUID = " + userUUID + ")", "pay");
            Logging.info("controller", requestBody.getParameter("signature"));
            try {
                // Если экшн-пэй, то проверяем сектор на активность -> Проверяем Ip -> Проверяем сигнатуру
                List<String> paymentParamsNames = Arrays.asList("sectorId", "amount", "desc");
                checkService.checkAll(requestBody, userUUID, sector, paymentParamsNames);

                return "redirect:/notifySubmitter";
            } catch (PaymentException e) {
                Logging.info("getPayment(userUUID = " + userUUID + ')', e.getMessage());
            }
        }

        return "payment";
    }


    @GetMapping("/notifySubmitter")
    public String getNotify() {

        return "notify";
    }

}
