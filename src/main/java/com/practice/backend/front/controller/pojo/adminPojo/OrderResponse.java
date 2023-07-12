package com.practice.backend.front.controller.pojo.adminPojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class OrderResponse {

    @NotNull
    private Long id;

    @NotNull
    private Long amount;

    @NotNull
    private Long fee;

    @NotNull
    @Pattern(regexp = "[\\p{Print}]{0,255}")
    private String description;

    @Pattern(regexp = "[\\p{Print}]{0,255}")
    @NotNull
    private String email;

    @NotNull
    private String paymentSystem;

    @NotNull
    private String state;

    public OrderResponse(@NotNull Long id, @NotNull Long amount, @NotNull Long fee, @NotNull String description, @NotNull String email, @NotNull String paymentSystem, @NotNull String state) {
        this.id = id;
        this.amount = amount;
        this.fee = fee;
        this.description = description;
        this.email = email;
        this.paymentSystem = paymentSystem;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(@NotNull Long amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(@NotNull Long fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public String getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(@NotNull String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public String getState() {
        return state;
    }

    public void setState(@NotNull String state) {
        this.state = state;
    }
}
