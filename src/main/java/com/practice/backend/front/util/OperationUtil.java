package com.practice.backend.front.util;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/** Отвечает за действиями над полями операции */
@Service
public class OperationUtil {

    /** Подсчитывает комиссию по формуле fee = amount - max(amount * percent + fix; notLess)*/
    public Long countFee(Long amount, String percent, String fix, String notLess) throws NumberFormatException {
        BigDecimal percentNumeric = castFeeComponentToDec(percent);
        BigDecimal fixNumeric = castFeeComponentToDec(fix);
        BigDecimal notLessNumeric = castFeeComponentToDec(notLess);

        return amount - (notLessNumeric.max(
                (percentNumeric.multiply(BigDecimal.valueOf(amount)))
                        .add(fixNumeric)).longValue()
        );
    }

    private BigDecimal castFeeComponentToDec(String component) throws NumberFormatException {
        BigDecimal componentNumeric;

        if (component == null) {
            componentNumeric = new BigDecimal(0);
        } else {
            componentNumeric = new BigDecimal(component);
        }

        return componentNumeric;
    }

}
