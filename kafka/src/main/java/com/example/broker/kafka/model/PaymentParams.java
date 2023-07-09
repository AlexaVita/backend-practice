package com.example.broker.kafka.model;

public class PaymentParams {

    private Long sectorId;

    private Double amount;

    private String description;

    private Long fee;

    private String email;

    public String getDescription() {
        return description;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getFee() {
        return fee;
    }

    public String getEmail() {
        return email;
    }
}
