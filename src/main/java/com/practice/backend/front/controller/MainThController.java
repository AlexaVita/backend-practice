package com.practice.backend.front.controller;

import com.practice.backend.front.controller.pojo.PaymentParams;
import com.practice.backend.exception.PaymentException;
import com.practice.backend.logging.Logging;
import com.practice.backend.front.service.CheckService;
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
    Logging logger;

    /**
     * Запрос '/api/hello' возвращает hello_world.html с дефолтным текстом "Hello World!",
     * если изменить запрос на '/api/hello?name=MyName', то получим изменённую страницу по шаблону,
     * которая теперь будет выводить "Hello MyName!"
     */
    @GetMapping("/hello")
    public String hello(@RequestAttribute(name = "uuid") UUID uuid, @RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello_world";
    }

    @GetMapping("/Payment")
    public String getPayment(@RequestAttribute(name = "uuid") UUID uuid, HttpServletRequest requestBody, PaymentParams paymentParams, Model model) {
        // Получаем основные параметры из запроса
        Long sectorId = paymentParams.getSectorId();
        Double amount = paymentParams.getAmount();
        String description = paymentParams.getDescription();

        // Если не экшн-пэй
        if (!"pay".equals(requestBody.getParameter("action"))) {
            try {
                // Проверяем сектор на активность -> Проверяем Ip -> Высчитываем сигнатуру
                List<String> paymentParamsNames = Arrays.asList("sectorId", "amount", "desc");
                String encodedSignature = checkService.checkIpAndSectorActiveAndGetEncodedSignature(requestBody, uuid, sectorId, paymentParamsNames);

                // Заполняем модель, в т.ч. закидываем высчитанную сигнатуру
                model.addAttribute("sectorId", sectorId);
                model.addAttribute("amount", amount);
                model.addAttribute("desc", description);

                //model.addAttribute("name", sector.getName());
                model.addAttribute("signature", encodedSignature);

            } catch (PaymentException e) {
                logger.error(uuid, "getPayment(action != pay)", e.getMessage());
            }
        } else {
            logger.debug(uuid, "getPayment(action == pay)", "pay with signature");
            try {
                // Если экшн-пэй, то проверяем сектор на активность -> Проверяем Ip -> Проверяем сигнатуру
                List<String> paymentParamsNames = Arrays.asList("sectorId", "amount", "desc");
                checkService.checkAll(requestBody, uuid, sectorId, paymentParamsNames);

                return "redirect:/notifySubmitter";
            } catch (PaymentException e) {
                logger.info(uuid, "getPayment(action == pay)", e.getMessage());
            }
        }

        return "payment";
    }


    @GetMapping("/notifySubmitter")
    public String getNotify() {

        return "notify";
    }

}
