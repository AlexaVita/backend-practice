package com.practice.backend.service;

import com.practice.backend.exception.PaymentException;
import com.practice.backend.model.Sector;
import com.practice.backend.util.PaymentUtil;
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

    public void checkIpAndSectorActive(HttpServletRequest request, UUID uuid, Sector sector) throws PaymentException {
        paymentUtil.checkActive(uuid, sector);
        paymentUtil.checkIp(request.getRemoteAddr(), uuid, sector);
    }

    public void checkAll(HttpServletRequest request, UUID uuid, Sector sector, List<String> paymentParamsNames) throws PaymentException {
        checkIpAndSectorActive(request, uuid, sector);
        paymentUtil.checkSignature(request, uuid, sector, paymentParamsNames);
    }

    public String checkIpAndSectorActiveAndGetEncodedSignature(HttpServletRequest request, UUID uuid, Sector sector, List<String> paymentParamsNames) throws PaymentException {
        checkIpAndSectorActive(request, uuid, sector);
        return paymentUtil.getEncodedSignature(request, uuid, sector, paymentParamsNames);
    }

}
