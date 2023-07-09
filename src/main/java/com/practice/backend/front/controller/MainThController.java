package com.practice.backend.front.controller;

import com.practice.backend.dao.model.Operation;
import com.practice.backend.dao.service.OperationService;
import com.practice.backend.enums.OperationStates;
import com.practice.backend.enums.OperationTypes;
import com.practice.backend.enums.PaymentExceptionCodes;
import com.practice.backend.exception.DatabaseException;
import com.practice.backend.exception.PaymentException;
import com.practice.backend.front.controller.pojo.PaymentParams;
import com.practice.backend.front.service.CheckService;
import com.practice.backend.front.service.KafkaService;
import com.practice.backend.logging.Logging;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.KafkaReplyTimeoutException;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Controller
public class MainThController {

    @Autowired
    CheckService checkService;

    @Autowired
    OperationService operationService;

    @Autowired
    Logging logger;

    @Autowired
    KafkaService kafkaService;

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
    public String getPayment(@RequestAttribute(name = "uuid") UUID uuid, HttpServletRequest requestBody, PaymentParams paymentParams,
                             RedirectAttributes redirectAttributes, Model model) throws ExecutionException, InterruptedException {

        // Получаем основные параметры из запроса
        Long sectorId = paymentParams.getSectorId();
        Long amount = paymentParams.getAmount();
        String description = paymentParams.getDescription();
        Long fee = paymentParams.getFee();
        String email = paymentParams.getEmail();

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

                // Приложение с рандомом может заснуть на 20 секунд (если не ок). Мы можем увеличить время ожидания в application.yml,
                // или просто оставить стандартные 5 секунд и обработку этого исключения
                ConsumerRecord<String, String> response;
                try {
                    response = kafkaService.sendMessageAndWaitForResponse(paymentParams.getDescription(), paymentParams);
                } catch (ExecutionException | InterruptedException e) {
                    model.addAttribute("error", new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, uuid, "Внутренняя ошибка"));
                    return "error";
                }

                OperationStates status;

                // Удалим все кавычки из ответа
                String responseValue = response.value().replaceAll("\"", "");

                // Если приложение нам дало неподходящий ответ, сообщаем об ошибке
                try {
                    status = OperationStates.valueOf(responseValue);
                } catch (IllegalArgumentException e) {
                    model.addAttribute("error", new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, uuid, "Внутренняя ошибка: неверный статус операции"));
                    return "error";
                }

                String panMask;

                // Достаём номер карты, проверяем на null, проверяем номер, генерируем маску карты
                try {
                    String pan = paymentParams.getPan();

                    if (pan == null) {
                        throw new PaymentException(PaymentExceptionCodes.INVALID_CARD, uuid, "Неверная карта!");
                    }

                    panMask = checkService.checkPanAndGetMask(uuid, pan);
                } catch (PaymentException e) {
                    model.addAttribute("error", e);
                    return "error";
                }

                fee = fee == null ? 0L : fee;
                email = email == null ? "" : email;

                Operation operation = new Operation(0L, sectorId, new Timestamp(System.currentTimeMillis()), amount,
                        fee, description, email, status, OperationTypes.PAYMENT, panMask);

                operationService.insert(operation);

                logger.info(uuid, "getPayment(action == pay)", response.value());

                redirectAttributes.addAttribute("status", responseValue);
                redirectAttributes.addAttribute("operationId", operation.getId());
                return "redirect:/notifySubmitter";
            } catch (PaymentException e) {
                model.addAttribute("error", e);
                return "error";
            }
        }

        return "payment";
    }


    @GetMapping("/notifySubmitter")
    public String getNotify(@RequestParam(name = "status") String status, @RequestParam(name = "operationId") Long operationId, Model model) {
        model.addAttribute("status", status);

        Operation operation;

        try {
            operation = operationService.getById(operationId);
        } catch (DatabaseException e) {
            model.addAttribute("error", e);
            return "error";
        }

        model.addAttribute("pan_mask", operation.getPan_mask());
        model.addAttribute("operationId", operationId);

        return "notify";
    }

}
