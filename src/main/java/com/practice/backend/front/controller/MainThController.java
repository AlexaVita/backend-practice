package com.practice.backend.front.controller;

import com.practice.backend.dao.model.Operation;
import com.practice.backend.dao.service.OperationService;
import com.practice.backend.enums.OperationStates;
import com.practice.backend.enums.OperationTypes;
import com.practice.backend.enums.PaymentExceptionCodes;
import com.practice.backend.exception.DatabaseException;
import com.practice.backend.exception.PaymentException;
import com.practice.backend.front.controller.pojo.NotifyParams;
import com.practice.backend.front.controller.pojo.PaymentParams;
import com.practice.backend.front.service.CheckService;
import com.practice.backend.front.service.KafkaService;
import com.practice.backend.logging.Logging;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.KafkaReplyTimeoutException;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;
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

    private final List<String> paymentParamsNames = Arrays.asList("sectorId", "amount", "desc");

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
        String encodedSignature;

        // Если не экшн-пэй
        if (!"pay".equals(requestBody.getParameter("action"))) {
            try {
                // Проверяем сектор на активность -> Проверяем Ip -> Высчитываем сигнатуру
                List<String> paymentParamsNames = Arrays.asList("sectorId", "amount", "desc");
                encodedSignature = checkService.checkIpAndSectorActiveAndGetEncodedSignature(requestBody, uuid, sectorId, paymentParamsNames);

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
            try {
                // Если экшн-пэй, то проверяем сектор на активность -> Проверяем Ip -> Проверяем сигнатуру
                encodedSignature = checkService.checkIpAndSectorActiveAndGetEncodedSignature(requestBody, uuid, sectorId, paymentParamsNames);

                String responseValue;
                OperationStates status;

                // Приложение с рандомом может заснуть на 20 секунд (если не ок). Мы можем увеличить время ожидания в application.yml,
                // или просто оставить стандартные 5 секунд и обработку этого исключения
                ConsumerRecord<String, String> response;
                try {
                    response = kafkaService.sendMessageAndWaitForResponse(paymentParams.getDescription(), paymentParams);

                    // Удалим все кавычки из ответа
                    responseValue = response.value().replaceAll("\"", "");

                    // Кастим ответ к enum (исключение обрабатывается)
                    status = OperationStates.valueOf(responseValue);

                } catch (ExecutionException | InterruptedException | IllegalArgumentException e) {
                    model.addAttribute("error", new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, uuid, "Внутренняя ошибка"));
                    return "error";
                }

                // Достаём номер карты, проверяем на null, проверяем номер, генерируем маску карт
                String pan = paymentParams.getPan();

                if (pan == null) {
                    throw new PaymentException(PaymentExceptionCodes.INVALID_CARD, uuid, "Неверная карта!");
                }

                String panMask = checkService.checkPanAndGetMask(uuid, pan);

                // Значения по умолчанию для fee и email
                fee = fee == null ? 0L : fee;
                email = email == null ? "" : email;

                // Генерируем операцию и записываем её в БД
                Operation operation = new Operation(0L, sectorId, new Timestamp(System.currentTimeMillis()), amount,
                        fee, description, email, status, OperationTypes.PAYMENT, panMask);

                operationService.insert(operation);

                NotifyParams notifyParams = new NotifyParams(responseValue, operation.getId(), sectorId, null);

                logger.info(uuid, "api/payment&action=pay", notifyParams.toString());

                // Добавляем аттрибут в модель - параметры страницы notify
                redirectAttributes.addFlashAttribute(notifyParams);
                // Поскольку необходимо выполнять проверку входящих в requestBody параметров сигнатуры, их прокидываем через addAttribute();
                Map<String, String> signatureParams = new HashMap<>();
                signatureParams.put("sectorId", sectorId.toString());
                signatureParams.put("amount", amount.toString());
                signatureParams.put("description", description);
                signatureParams.put("signature", encodedSignature);
                redirectAttributes.mergeAttributes(signatureParams);

                return "redirect:/notifySubmitter";
            } catch (PaymentException e) {
                model.addAttribute("error", e);
                return "error";
            }
        }

        return "payment";
    }

    @GetMapping("/notifySubmitter")
    public String getNotify(@RequestAttribute(name = "uuid") UUID uuid, HttpServletRequest requestBody,
                            NotifyParams notifyParams, Model model) {

        Operation operation;

        try {
            // Проверка сигнатуры, IP, активности сектора
            checkService.checkAll(requestBody, uuid, notifyParams.getSectorId(), paymentParamsNames);
            // Берём операцию из БД
            operation = operationService.getById(notifyParams.getOperationId());
        } catch (PaymentException | DatabaseException e) {
            model.addAttribute("error", e);
            return "error";
        }

        notifyParams.setPanMask(operation.getPan_mask());

        return "notify";
    }

}
