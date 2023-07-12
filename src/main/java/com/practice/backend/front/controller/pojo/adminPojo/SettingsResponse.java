package com.practice.backend.front.controller.pojo.adminPojo;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SettingsResponse {

    @NotNull
    boolean email;

    @NotNull
    boolean alternativePayment;

    @NotNull
    List<Commission> commissions;

    public SettingsResponse(@NotNull boolean email, @NotNull boolean alternativePayment, @NotNull List<Commission> commissions) {
        this.email = email;
        this.alternativePayment = alternativePayment;
        this.commissions = commissions;
    }

    @NotNull
    public boolean isEmail() {
        return email;
    }

    public void setEmail(@NotNull boolean email) {
        this.email = email;
    }

    @NotNull
    public boolean isAlternativePayment() {
        return alternativePayment;
    }

    public void setAlternativePayment(@NotNull boolean alternativePayment) {
        this.alternativePayment = alternativePayment;
    }

    @NotNull
    public List<Commission> getCommissions() {
        return commissions;
    }

    public void setCommissions(@NotNull List<Commission> commissions) {
        this.commissions = commissions;
    }
}
