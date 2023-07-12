package com.practice.backend.front.controller;

import com.practice.backend.dao.model.Fee;
import com.practice.backend.dao.model.Operation;
import com.practice.backend.dao.model.Sector;
import com.practice.backend.dao.model.SectorSettingsMap;
import com.practice.backend.dao.service.FeeService;
import com.practice.backend.dao.service.OperationService;
import com.practice.backend.dao.service.SectorService;
import com.practice.backend.dao.service.SectorSettingsMapService;
import com.practice.backend.enums.SectorSettingMapName;
import com.practice.backend.exception.PaymentException;
import com.practice.backend.front.controller.pojo.adminPojo.Commission;
import com.practice.backend.front.controller.pojo.adminPojo.OrderResponse;
import com.practice.backend.front.controller.pojo.adminPojo.SettingsResponse;
import com.practice.backend.front.util.OperationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AdminController {

    @Autowired
    SectorService sectorService;

    @Autowired
    OperationService operationService;

    @Autowired
    SectorSettingsMapService sectorSettingsMapService;

    @Autowired
    OperationUtil operationUtil;

    @Autowired
    FeeService feeService;

//    Операции:
    @GetMapping(value = "/orders", produces = APPLICATION_JSON_VALUE)
    public List<OrderResponse> getOrders(@RequestAttribute("uuid") UUID uuid) throws PaymentException {

        List<OrderResponse> response = new ArrayList<>();

        List<Operation> operations = operationService.getAll();

        // Добавляем все
        for (Operation operation : operations) {

            response.add(new OrderResponse(
                    operation.getId(),
                    operation.getAmount(),
                    operation.getFee(),
                    operation.getDescription(),
                    operation.getEmail(),
                    operation.getPaymentSystem().name(),
                    operation.getState().name()
            ));

        }

        return response;
    }

//    Настройки:

    @GetMapping(value = "/settings", produces = APPLICATION_JSON_VALUE)
    public List<SettingsResponse> getSettings(@RequestAttribute("uuid") UUID uuid) throws PaymentException {

        List<SettingsResponse> response = new ArrayList<>();

        List<Sector> sectors = sectorService.getAll();

        for (Sector sector : sectors) {
            List<SectorSettingsMap> settings = sectorSettingsMapService.getBySectorId(sector.getSectorId());
            List<Commission> commissions = new ArrayList<>();

            boolean showEmail = false, alternatePay = false;

            for (SectorSettingsMap setting : settings) {
                // Ищем настройку SHOW_EMAIL
                if (SectorSettingMapName.SHOW_EMAIL.name().equals(setting.getName())) {
                    // Если её значение - true, то показываем почту
                    if ("true".equals(setting.getValue())) {
                        showEmail = true;
                    }

                    continue;
                }

                // Ищем настройку SUAI_PAY
                if (SectorSettingMapName.SUAI_PAY.name().equals(setting.getName())) {
                    // Если её значение - true, то показываем почту
                    if ("true".equals(setting.getValue())) {
                        alternatePay = true;
                    }
                }
            }

            List<Fee> fees = feeService.getBySectorId(sector.getSectorId());

            for (Fee fee : fees) {
                commissions.add(new Commission(
                        fee.getId(),
                        fee.getPaymentSystem().name(),
                        new BigDecimal(fee.getFix()).longValue(), // Фронт просит fee (конкретную комиссию), но скорее тут нужны fix, notLess и percent???
                        new BigDecimal(fee.getNotLess()).longValue()
                ));
            }

            response.add(new SettingsResponse(
                    showEmail,
                    alternatePay,
                    commissions
            ));
        }

        return response;
    }

//
//                2) Изменить настройки
//    url: "/settings",
//    method: PUT,
//    data: [
//    {
//        "email": boolean
//        "alternative_payment": boolean
//        "commissions": [
//        { "id": 1, "payment_system": "VISA", "fee": 5, "min_bet": 219},
//        { "id": 2, "payment_system": "MasterCard", "fee": 11, "min_bet": 10},
//        { "id": 3, "payment_system": "Мир", "fee": 9, "min_bet": 150}
//       			]
//    }
//		]

}
