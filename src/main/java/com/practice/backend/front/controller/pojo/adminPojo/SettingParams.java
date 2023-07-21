package com.practice.backend.front.controller.pojo.adminPojo;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SettingParams {

    @NotNull
    boolean email;

    @NotNull
    boolean alternativePayment;

    @NotNull
    String storeLogo;

    public SettingParams(@NotNull boolean email, @NotNull boolean alternativePayment, @NotNull String storeLogo) {
        this.email = email;
        this.alternativePayment = alternativePayment;
        this.storeLogo = storeLogo;
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
    public String getStoreLogo() {
        return storeLogo;
    }

    @NotNull
    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }
}
