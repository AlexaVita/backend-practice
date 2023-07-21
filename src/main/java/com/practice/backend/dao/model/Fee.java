package com.practice.backend.dao.model;

import com.practice.backend.enums.PaymentSystem;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class Fee implements IGuapEntity {

    @NotNull
    private Long id;

    @NotNull
    private Long sectorId;

    @NotNull
    private PaymentSystem paymentSystem;

    private String percent;

    private String fix;

    private String notLess;

    public Fee(@NotNull Long id, @NotNull Long sectorId, @NotNull PaymentSystem paymentSystem, String percent, String fix, String notLess) {
        this.id = id;
        this.sectorId = sectorId;
        this.paymentSystem = paymentSystem;
        this.percent = percent;
        this.fix = fix;
        this.notLess = notLess;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getFix() {
        return fix;
    }

    public void setFix(String fix) {
        this.fix = fix;
    }

    public String getNotLess() {
        return notLess;
    }

    public void setNotLess(String notLess) {
        this.notLess = notLess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fee feeDto = (Fee) o;
        return Objects.equals(id, feeDto.id) &&
                Objects.equals(sectorId, feeDto.sectorId) &&
                Objects.equals(paymentSystem, feeDto.paymentSystem) &&
                Objects.equals(percent, feeDto.percent) &&
                Objects.equals(fix, feeDto.fix) &&
                Objects.equals(notLess, feeDto.notLess);
    }

    @Override
    public String toString() {
        return "Fee{" +
                "id=" + id +
                ", sectorId=" + sectorId +
                ", paymentSystem='" + paymentSystem + '\'' +
                ", percent='" + percent + '\'' +
                ", fix='" + fix + '\'' +
                ", notLess='" + notLess + '\'' +
                '}';
    }
}
