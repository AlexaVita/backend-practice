package com.practice.backend.front.controller;

import com.practice.backend.dao.model.Fee;
import com.practice.backend.dao.model.Operation;
import com.practice.backend.dao.model.Sector;
import com.practice.backend.dao.service.FeeService;
import com.practice.backend.dao.service.OperationService;
import com.practice.backend.dao.service.SectorService;
import com.practice.backend.dao.service.SectorSettingsMapService;
import com.practice.backend.enums.PaymentExceptionCodes;
import com.practice.backend.enums.PaymentSystem;
import com.practice.backend.enums.SectorSettingMapName;
import com.practice.backend.exception.DatabaseException;
import com.practice.backend.exception.PaymentException;
import com.practice.backend.front.controller.pojo.adminPojo.CommissionParams;
import com.practice.backend.front.controller.pojo.adminPojo.OrderResponse;
import com.practice.backend.front.controller.pojo.adminPojo.SettingParams;
import com.practice.backend.front.util.OperationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AdminController {

    SectorService sectorService;

    OperationService operationService;

    SectorSettingsMapService sectorSettingsMapService;

    OperationUtil operationUtil;

    FeeService feeService;

    @Autowired
    public AdminController(SectorService sectorService, OperationService operationService, SectorSettingsMapService sectorSettingsMapService, OperationUtil operationUtil, FeeService feeService) {
        this.sectorService = sectorService;
        this.operationService = operationService;
        this.sectorSettingsMapService = sectorSettingsMapService;
        this.operationUtil = operationUtil;
        this.feeService = feeService;
    }

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
    public List<SettingParams> getSettings(@RequestAttribute("uuid") UUID uuid) throws PaymentException {

        List<SettingParams> response = new ArrayList<>();

        List<Sector> sectors = sectorService.getAll();

        for (Sector sector : sectors) {

            boolean showEmail = sectorSettingsMapService.getSettingValueWithNameAndSectorId(
                    SectorSettingMapName.SHOW_EMAIL.name(), sector.getSectorId()).equals("true");

            boolean alternatePay = sectorSettingsMapService.getSettingValueWithNameAndSectorId(
                    SectorSettingMapName.SUAI_PAY.name(), sector.getSectorId()).equals("true");

            String storeLogo = sectorSettingsMapService.getSettingValueWithNameAndSectorId(
                    SectorSettingMapName.STORE_LOGO.name(), sector.getSectorId());


            response.add(new SettingParams(
                    showEmail,
                    alternatePay,
                    storeLogo
            ));
        }

        return response;
    }

    // PUT не могу написать, потому что фронт не кидает sectorId

    //@PutMapping("/settings")
    //public void updateSetting(@RequestAttribute("uuid") UUID uuid, @RequestBody ArrayList<SettingParams> settingParams) throws PaymentException {
    //    // Для каждой настройки, которую необходимо обновить
    //    for (SettingParams requestSetting : settingParams) {
    //        // Получаем список данных о комиссии
    //        List<CommissionParams> commissionParams = requestSetting.getCommissions();
//
//
    //        if (commissionParams.isEmpty()) {
    //            throw new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, uuid, "В настройке нет информации о комиссии!");
    //        }
//
    //        List<SectorSettingsMap> settingsFromDB;
    //        Long sectorId;
//
    //        try {
    //            // Из запроса получаем id любой из комиссий, берём её из БД
    //            Fee fee = feeService.getById(commissionParams.get(0).getId());
//
    //            // Для комиссии находим id сектора, а по нему - sectorSettingsMap (для любой записи из запроса sectorId один и тот же
    //            sectorId = fee.getSectorId();
    //            settingsFromDB = sectorSettingsMapService.getBySectorId(sectorId);
//
    //        } catch (DatabaseException e) {
    //            throw new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, uuid, "В настройке нет информации о комиссии!");
    //        }
//
    //        boolean emailUpdated = false, alternatePayUpdated = false;
//
    //        for (SectorSettingsMap settingFromDB : settingsFromDB) {
    //            if (SectorSettingMapName.SHOW_EMAIL.name().equals(settingFromDB.getName())) {
    //                settingFromDB.setValue(String.valueOf(requestSetting.isEmail()));
    //                emailUpdated = true;
    //                continue;
    //            }
//
    //            if (SectorSettingMapName.SUAI_PAY.name().equals(settingFromDB.getName())) {
    //                settingFromDB.setValue(String.valueOf(requestSetting.isAlternativePayment()));
    //                alternatePayUpdated = true;
    //            }
    //        }
//
    //        if (!emailUpdated) {
    //            sectorSettingsMapService.insert(new SectorSettingsMap(0L,
    //                    sectorId, SectorSettingMapName.SHOW_EMAIL.name(), String.valueOf(requestSetting.isEmail())));
    //        }
//
    //        if (!alternatePayUpdated) {
    //            sectorSettingsMapService.insert(new SectorSettingsMap(0L,
    //                    sectorId, SectorSettingMapName.SUAI_PAY.name(), String.valueOf(requestSetting.isAlternativePayment())));
    //        }
//
    //    }
    //}

    @GetMapping(value = "/commissions", produces = APPLICATION_JSON_VALUE)
    public List<CommissionParams> getCommissions(@RequestAttribute("uuid") UUID uuid) throws PaymentException {

        List<CommissionParams> response = new ArrayList<>();

        List<Fee> fees = feeService.getAll();

        for (Fee fee : fees) {
            response.add(new CommissionParams(
                    fee.getId(),
                    fee.getPaymentSystem().name(),
                    new BigDecimal(fee.getPercent()).longValue(), // процент
                    new BigDecimal(fee.getNotLess()).longValue()  // не меньше
            ));
        }

        return response;
    }

    @PutMapping( "/commissions")
    public void insertCommission(@RequestAttribute("uuid") UUID uuid, @RequestBody CommissionParams newCommission) throws PaymentException {

        try {
            Fee newFee = feeService.getById(newCommission.getId());

            newFee.setPercent(newCommission.getFee().toString());
            newFee.setPaymentSystem(PaymentSystem.valueOf(newCommission.getPaymentSystem()));
            newFee.setNotLess(newCommission.getMinBet().toString());

            feeService.update(newFee);

        } catch (DatabaseException | DuplicateKeyException e) {
            throw new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, uuid, "Внутренняя ошибка БД. Ключ повторяется или не был найден");
        }

    }

    // POST не могу написать, потому что фронт не кидает sector_id


    @ExceptionHandler({ PaymentException.class })
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public void handleException() {

    }
}
