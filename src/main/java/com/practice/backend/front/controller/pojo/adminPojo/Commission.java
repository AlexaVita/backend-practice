package com.practice.backend.front.controller.pojo.adminPojo;

import jakarta.validation.constraints.NotNull;

public class Commission {

    @NotNull
    Long id;


    @NotNull
    String paymentSystem;

    @NotNull
    Long minBet;

    @NotNull
    Long fee; // Возможно подразумевается fix ???

    public Commission(@NotNull Long id, @NotNull String paymentSystem, @NotNull Long minBet, @NotNull Long fee) {
        this.id = id;
        this.paymentSystem = paymentSystem;
        this.minBet = minBet;
        this.fee = fee;
    }

    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    @NotNull
    public String getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(@NotNull String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    @NotNull
    public Long getMinBet() {
        return minBet;
    }

    public void setMinBet(@NotNull Long minBet) {
        this.minBet = minBet;
    }

    @NotNull
    public Long getFee() {
        return fee;
    }

    public void setFee(@NotNull Long fee) {
        this.fee = fee;
    }
}
