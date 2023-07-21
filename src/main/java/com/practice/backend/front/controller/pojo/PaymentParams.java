package com.practice.backend.front.controller.pojo;

import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

/** Содержит список параметров для платежа */
public class PaymentParams {

    @NonNull
    private Long sectorId;

    @NonNull
    private Long amount;

    @NonNull
    private String description;

    private Long fee;

    private String email;

    // UUID reference (?) зависит от фронта

    // enum currency (?) если нечем будет заняться

    private String pan;

    public PaymentParams(@NotNull Long sectorId, @NotNull Long amount, @NotNull String description, Long fee, String email, String pan) {
        this.sectorId = sectorId;
        this.amount = amount;
        this.description = description;
        this.fee = fee;
        this.email = email;
        this.pan = pan;
    }

    public @NotNull Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(@NotNull Long sectorId) {
        this.sectorId = sectorId;
    }

    public @NotNull Long getAmount() {
        return amount;
    }

    public void setAmount(@NotNull Long amount) {
        this.amount = amount;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
