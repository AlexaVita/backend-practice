package com.practice.backend.util;

import com.practice.backend.controller.MainThController;
import com.practice.backend.enums.PaymentExceptionCodes;
import com.practice.backend.exception.PaymentException;
import com.practice.backend.model.Sector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class PaymentUtil {

    Logger logger = LoggerFactory.getLogger(MainThController.class);

    public void checkPan(UUID uuid, String pan) throws PaymentException {
        //превращаем строку в массив чисел + проверка чтобы все были цифрами
        if (!pan.matches("\\d+")) {
            throw new PaymentException(PaymentExceptionCodes.INVALID_CARD, uuid, "Неверно заполнена карта");
        }

        int[] digits = pan.chars().
                mapToObj(c -> (char) c)
                .mapToInt(Character::getNumericValue)
                .toArray();

        //проверка на бизнес-требования
        if (digits.length < 16 || digits.length > 19 || (digits[0] != 2 && digits[0] != 4 && digits[0] != 5))
            throw new PaymentException(PaymentExceptionCodes.INVALID_CARD, uuid, "Неверно заполнена карта");

        //алгоритм Луна
        algLuhn(digits, uuid);
    }

    // Алгоритм Луна
    public void algLuhn(int[] digits, UUID uuid) throws PaymentException {
        int sum = 0;
        int parity = digits.length % 2;
        for (int i = 0; i <= digits.length - 1; i++) {

            if (i % 2 == parity) {
                digits[i] = digits[i] * 2;
                if (digits[i] > 9)
                    digits[i] = digits[i] - 9;
            }
            sum += digits[i];
        }
        if (sum % 10 != 0) {
            throw new PaymentException(PaymentExceptionCodes.INVALID_CARD, uuid, "Неверно заполнена карта");
        }
    }

    public void checkIp(String remoteAddr, UUID userUUID, Sector paymentSector) throws PaymentException {
        if (Boolean.TRUE.equals(paymentSector.getCheckIp())) {
            String allowedIps = paymentSector.getAllowedIps();
            // Если IP из реквеста не содержится в строке allowedIps экземпляра класса Sector, то кидаем "абстрактную" ошибку
            //if (!allowedIps.contains(request.getRemoteAddr())) {
            if (allowedIps!=null && !allowedIps.contains(remoteAddr)) {
                throw new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, userUUID, "Внутренняя ошибка");
            }
        }
    }

    public void checkActive(UUID userUUID, Sector paymentSector) throws PaymentException {
        if (!Boolean.TRUE.equals(paymentSector.getActive())) {
            throw new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, userUUID, "Внутренняя ошибка");
        }
    }

    public String getPanMask(String pan) {
        // По правилу 6 в начале, 4 в конце
        String mask = "*";
        int start = 6;
        int end = pan.length() - 4;
        // Итоговая строка - первые 6 символов + маска (любые символы ["."] между на *) + последние 4 символа
        return pan.substring(0, start) + pan.substring(start, end).replaceAll(".", mask) + pan.substring(end);
    }

    /** Проверяет соответствие пересчитанной сигнатуры и входящей в запрос сигнатуры */
    public void checkSignature(HttpServletRequest request, UUID userUUID, Sector paymentSector, List<String> paymentParamsNames) throws PaymentException {
        String countedSignature = getEncodedSignature(request, userUUID, paymentSector, paymentParamsNames);
        String initialSignature = (String) request.getParameter("signature");
        logger.info("counted "+countedSignature);
        logger.info("from controller "+initialSignature);

        // Если начальная сигнатура и конечная не совпадают, то прокидываем исключение
        if (!countedSignature.equals(initialSignature)) {
            throw new PaymentException(PaymentExceptionCodes.INVALID_SIGNATURE, userUUID, "Неверная сигнатура");
        }
    }

    /** Возвращает закодированную сигнатуру как SHA256 -> Base64 */
    public String getEncodedSignature(HttpServletRequest request, UUID userUUID, Sector paymentSector, List<String> paymentParamsNames) throws PaymentException {
        try {
            String initialSignature = buildSignature(request, paymentSector.getSignCode(), paymentParamsNames);
            // Результат - конвертация в строку через Base64 результата конвертации начальной строки через SHA256 в массив байтов
            return convertThroughBase64(convertThroughSHA256(initialSignature));
        } catch (NoSuchAlgorithmException e) {
            throw new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, userUUID, "Внутренняя ошибка");
        }
    }

    /** Высчитывает сигнатуру на основе запроса, signCode и списка имён параметров */
    private String buildSignature(HttpServletRequest request, String signCode, List<String> paymentParamsNames) {
        StringBuilder signatureBuilder = new StringBuilder();

        for (String paramName : paymentParamsNames) {
            Object requestParam = request.getParameter(paramName);

            if (requestParam == null) {
                requestParam = "";
            }
            // По списку имён аттрибутов получаем значения параметров из запроса
            signatureBuilder.append((String) requestParam);
        }

        // Последним в сигнатуре добавляется SignCode сектора
        signatureBuilder.append(signCode);

        return signatureBuilder.toString();
    }

    private byte[] convertThroughSHA256(String initialString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(initialString.getBytes(StandardCharsets.UTF_8));
    }

    private String convertThroughBase64(byte[] encodedHash) {
        return Base64.getEncoder().encodeToString(encodedHash);
    }

}
