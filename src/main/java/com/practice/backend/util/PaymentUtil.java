package com.practice.backend.util;

import com.practice.backend.enums.PaymentExceptionCodes;
import com.practice.backend.exception.PaymentException;
import com.practice.backend.model.Sector;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class PaymentUtil {

    public static void checkSignature(HttpServletRequest request, Sector paymentSector, List<String> paymentParamsNames) throws PaymentException {
        // Берём сигнатуру из реквест-параметра
        String signature = (String) request.getAttribute("signature");

        StringBuilder signatureBuilder = new StringBuilder();

        for (String paramName : paymentParamsNames) {
            Object requestParam = request.getAttribute(paramName);

            if (requestParam == null) {
                requestParam = "";
            }
            // По списку имён аттрибутов получаем значения параметров из запроса
            signatureBuilder.append((String) requestParam);
        }

        String password = paymentSector.getSignCode();

        // Последним в сигнатуре добавляется SignCode сектора
        signatureBuilder.append(password);

        String countedSignature;

        // Для отслеживания ошибок
        UUID userUUID = (UUID) request.getAttribute("userUUID");

        try {
            // Результат - конвертация в строку через Base64 результата конвертации начальной строки через SHA256 в массив байтов
            countedSignature = convertThroughBase64(convertThroughSHA256(signatureBuilder.toString()));
        } catch (NoSuchAlgorithmException e) {
            throw new PaymentException(PaymentExceptionCodes.INTERNAL_ERROR, userUUID, "Внутренняя ошибка");
        }

        // Если начальная сигнатура и конечная не совпадают, то прокидываем исключение
        if (!countedSignature.equals(signature)) {
            throw new PaymentException(PaymentExceptionCodes.INVALID_SIGNATURE, userUUID, "Неверная сигнатура");
        }
    }

    private static byte[] convertThroughSHA256(String initialString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(initialString.getBytes(StandardCharsets.UTF_8));
    }

    private static String convertThroughBase64(byte[] encodedHash) {
        return Base64.getEncoder().encodeToString(encodedHash);
    }

}
