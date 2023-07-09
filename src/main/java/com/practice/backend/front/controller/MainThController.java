package com.practice.backend.front.controller;

import com.practice.backend.exception.PaymentException;
import com.practice.backend.front.controller.pojo.PaymentParams;
import com.practice.backend.front.service.CheckService;
import com.practice.backend.logging.Logging;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Controller
public class MainThController {

    private String requestTopic = "paymentParams";

    @Autowired
    private ReplyingKafkaTemplate<String, PaymentParams, String> replyingKafkaTemplate;


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
    public String getPayment(@RequestAttribute(name = "uuid") UUID uuid, HttpServletRequest requestBody, PaymentParams paymentParams, Model model) throws ExecutionException, InterruptedException {

        //PaymentParams paymentParams = new PaymentParams(2L, 2.0, "working!", 2L, "sd@gmail.com");
        ProducerRecord<String, PaymentParams> record = new ProducerRecord<>(requestTopic, null, paymentParams.getDescription(), paymentParams);
        RequestReplyFuture<String, PaymentParams, String> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, String> response = future.get();
        logger.info(uuid, "f", response.value());
        if (!response.value().equals("\"yes\""))
            return "error";


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
                model.addAttribute("error", e);
                return "error";
            }
        } else {
            logger.debug(uuid, "getPayment(action == pay)", "pay with signature");
            try {
                // Если экшн-пэй, то проверяем сектор на активность -> Проверяем Ip -> Проверяем сигнатуру
                List<String> paymentParamsNames = Arrays.asList("sectorId", "amount", "desc");
                checkService.checkAll(requestBody, uuid, sectorId, paymentParamsNames);

                return "redirect:/notifySubmitter";
            } catch (PaymentException e) {
                model.addAttribute("message", e);
                return "error";
            }
        }

        return "payment";
    }


    @GetMapping("/notifySubmitter")
    public String getNotify(Model model) {
        model.addAttribute("status", "success");
        return "notify";
    }

}
