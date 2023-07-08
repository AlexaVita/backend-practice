package com.practice.backend.front.service;

import com.practice.backend.enums.PaymentExceptionCodes;
import com.practice.backend.exception.DatabaseException;
import com.practice.backend.exception.PaymentException;
import com.practice.backend.dao.model.Sector;
import com.practice.backend.front.util.PaymentUtil;
import com.practice.backend.dao.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/** Просто класс-фасад для удобного вызова проверок */
@Service
public class CheckService {
    @Autowired
    PaymentUtil paymentUtil;

    @Autowired
    SectorService sectorService;


    public void checkIpAndSectorActive(String currentIP, UUID uuid, Sector sector) throws PaymentException {
        paymentUtil.checkActive(uuid, sector);
        paymentUtil.checkIp(currentIP, uuid, sector);
    }

    public void checkAll(HttpServletRequest request, UUID uuid, Long sectorId, List<String> paymentParamsNames) throws PaymentException {
        Sector sector;
        try {
            sector = sectorService.getById(sectorId);
        } catch (DatabaseException e) {
            throw new PaymentException(PaymentExceptionCodes.SECTOR_WAS_NOT_FOUND, uuid, e.getMessage());
        }
        checkIpAndSectorActive(request.getRemoteAddr(), uuid, sector);
        paymentUtil.checkSignature(request, uuid, sector, paymentParamsNames);
    }

    public String checkIpAndSectorActiveAndGetEncodedSignature(HttpServletRequest request, UUID uuid, Long sectorId, List<String> paymentParamsNames) throws PaymentException {
        Sector sector;
        try {
            sector = sectorService.getById(sectorId);
        } catch (DatabaseException e) {
            throw new PaymentException(PaymentExceptionCodes.SECTOR_WAS_NOT_FOUND, uuid, e.getMessage());
        }
        checkIpAndSectorActive(request.getRemoteAddr(), uuid, sector);
        return paymentUtil.getEncodedSignature(request, uuid, sector, paymentParamsNames);
    }

}
