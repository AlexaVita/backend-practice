package com.practice.backend.controller.pojo;

/** Содержит список параметров для платежа */
public class PaymentParams {

    private Long sectorId;

    private Double amount;

    private String description;

    private Long fee;

    private String email;

    // UUID reference (?) зависит от фронта

    // enum currency (?) если нечем будет заняться

    public PaymentParams(Long sectorId, Double amount, String description) {
        this.sectorId = sectorId;
        this.amount = amount;
        this.description = description;
        this.fee = 0L;
        this.email = "";
    }

    public PaymentParams(Long sectorId, Double amount, String description, Long fee, String email) {
        this.sectorId = sectorId;
        this.amount = amount;
        this.description = description;
        this.fee = fee;
        this.email = email;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
}
