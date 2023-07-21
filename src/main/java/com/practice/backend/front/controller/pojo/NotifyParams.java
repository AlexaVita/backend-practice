package com.practice.backend.front.controller.pojo;

import org.springframework.lang.NonNull;

public class NotifyParams {

    @NonNull
    private String status;

    @NonNull
    private Long sectorId;

    @NonNull
    private Long operationId;

    private String panMask;

    public NotifyParams(@NonNull String status, @NonNull Long operationId, @NonNull Long sectorId, String panMask) {
        this.status = status;
        this.operationId = operationId;
        this.sectorId = sectorId;
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

    public String getPanMask() {
        return panMask;
    }

    public void setPanMask(String panMask) {
        this.panMask = panMask;
    }

    @NonNull
    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(@NonNull Long sectorId) {
        this.sectorId = sectorId;
    }

    @Override
    public String toString() {
        return "NotifyParams{" +
                "status='" + status + '\'' +
                ", operationId=" + operationId +
                '}';
    }
}
