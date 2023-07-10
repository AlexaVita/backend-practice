package com.practice.backend.front.controller.pojo;

import org.springframework.lang.NonNull;

public class NotifyParams {

    @NonNull
    private String status;

    @NonNull
    private Long operationId;

    @NonNull
    private Long sectorId;

    @NonNull
    private Long amount;

    @NonNull
    private String description;

    @NonNull
    private String signature;

    private String panMask;

    public NotifyParams(@NonNull String status, @NonNull Long operationId, @NonNull Long sectorId, @NonNull Long amount, @NonNull String description, @NonNull String signature, String panMask) {
        this.status = status;
        this.operationId = operationId;
        this.sectorId = sectorId;
        this.amount = amount;
        this.description = description;
        this.signature = signature;
        this.panMask = panMask;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(@NonNull Long operationId) {
        this.operationId = operationId;
    }

    @NonNull
    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(@NonNull Long sectorId) {
        this.sectorId = sectorId;
    }

    @NonNull
    public Long getAmount() {
        return amount;
    }

    public void setAmount(@NonNull Long amount) {
        this.amount = amount;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getSignature() {
        return signature;
    }

    public void setSignature(@NonNull String signature) {
        this.signature = signature;
    }

    public String getPanMask() {
        return panMask;
    }

    public void setPanMask(String panMask) {
        this.panMask = panMask;
    }

    @Override
    public String toString() {
        return "NotifyParams{" +
                "status='" + status + '\'' +
                ", operationId=" + operationId +
                ", sectorId=" + sectorId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
