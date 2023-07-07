package com.practice.backend.exception;

import com.practice.backend.enums.PaymentExceptionCodes;

import java.util.UUID;

public class PaymentException extends Exception {

    int code;

    UUID userUUID;

    public PaymentException(PaymentExceptionCodes code, UUID userUUID, String message) {
        super(message);
        this.code = code.ordinal();
        this.userUUID = userUUID;
    }

    public PaymentException(PaymentExceptionCodes code, String message) {
        super(message);
        this.code = code.ordinal();
        this.userUUID = new UUID(0, 0);
    }

    @Override
    public String toString() {
        return "PaymentException{" +
                "code=" + code +
                "; message=" + super.getMessage() +
                "; userUUID=" + userUUID +
                '}';
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }
}
