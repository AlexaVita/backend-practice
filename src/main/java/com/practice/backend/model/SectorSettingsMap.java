package com.practice.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

public class SectorSettingsMap implements IGuapEntity {
    @NotNull
    private Long id;
    @NotNull
    private Long sectorId;
    @Pattern(regexp = "[\\p{Print}]{0,255}")
    private String name;
    @Pattern(regexp = "[\\p{Print}]{0,255}")
    private String value;
    private Boolean payment;
    @Pattern(regexp = "[\\p{Print}]{0,255}")
    private String binList;
    private Boolean guapPay;

    public SectorSettingsMap(Long id, Long sectorId, String name, String value, Boolean payment, String binList, Boolean guapPay) {
        this.id = id;
        this.sectorId = sectorId;
        this.name = name;
        this.value = value;
        this.payment = payment;
        this.binList = binList;
        this.guapPay = guapPay;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getPayment() {
        return payment;
    }

    public void setPayment(Boolean payment) {
        this.payment = payment;
    }

    public String getBinList() {
        return binList;
    }

    public void setBinList(String binList) {
        this.binList = binList;
    }

    public Boolean getGuapPay() {
        return guapPay;
    }

    public void setGuapPay(Boolean guapPay) {
        this.guapPay = guapPay;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectorSettingsMap that = (SectorSettingsMap) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sectorId, that.sectorId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(payment, that.payment) &&
                Objects.equals(binList, that.binList) &&
                Objects.equals(guapPay, that.guapPay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sectorId, name, value, payment, binList, guapPay);
    }

    @Override
    public String toString() {
        return "SectorSettingsMap{" +
                "id=" + id +
                ", sectorId=" + sectorId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", payment=" + payment +
                ", binList='" + binList + '\'' +
                ", guapPay=" + guapPay +
                '}';
        }
    }
