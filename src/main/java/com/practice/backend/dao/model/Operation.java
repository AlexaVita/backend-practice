package com.practice.backend.dao.model;

import com.practice.backend.enums.OperationStates;
import com.practice.backend.enums.OperationTypes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.sql.Timestamp;
import java.util.Objects;

public class Operation implements IGuapEntity {
    public Operation(@NotNull Long id, @NotNull Long sectorId, @NotNull Timestamp timestamp, Long amount, Long fee,
                     String description, String email, OperationStates state, OperationTypes type, String pan_mask) {
        this.id = id;
        this.sectorId = sectorId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.fee = fee;
        this.description = description;
        this.email = email;
        this.state = state;
        this.type = type;
        this.pan_mask = pan_mask;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OperationStates getState() {
        return state;
    }

    public void setState(OperationStates state) {
        this.state = state;
    }

    public OperationTypes getType() {
        return type;
    }

    public void setType(OperationTypes type) {
        this.type = type;
    }

    public String getPan_mask() { return pan_mask; }

    public void setPan_mask(String pan_mask) { this.pan_mask = pan_mask; }

    /*id bigserial PRIMARY KEY,
            sector_id bigint,
            date timestamp,
            amount numeric(19,0),
            fee numeric(19,0),
            description varchar(512),
            email varchar(128),
            state varchar(64),
            type varchar(64)*/
    @NotNull
    private Long id;
    @NotNull
    private Long sectorId;
    @NotNull
    private Timestamp timestamp;
    @NotNull
    private Long amount;
    @NotNull
    private Long fee;
    @Pattern(regexp = "[\\p{Print}]{0,255}")
    private String description;
    @Pattern(regexp = "[\\p{Print}]{0,255}")
    private String email;

    private OperationStates state;

    private OperationTypes type;


    @Pattern(regexp = "\\d{6}[*]{6}\\d{4}")
    private String pan_mask;

    @Override
    public String toString() {
        return "SectorDto{" +
                "id=" + id +
                ", sector_id='" + sectorId + '\'' +
                ", timestamp=" + timestamp +
                ", amount='" + amount + '\'' +
                ", fee=" + fee +
                ", description='" + description + '\'' +
                ", email=" + email +
                ", state='" + state + '\'' +
                ", type=" + type +
                ", pan_mask=" + pan_mask +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operationDto = (Operation) o;
        return Objects.equals(id, operationDto.id) &&
                Objects.equals(sectorId, operationDto.sectorId) &&
                Objects.equals(timestamp, operationDto.timestamp) &&
                Objects.equals(amount, operationDto.amount) &&
                Objects.equals(fee, operationDto.fee) &&
                Objects.equals(description, operationDto.description) &&
                Objects.equals(email, operationDto.email) &&
                Objects.equals(state, operationDto.state) &&
                Objects.equals(type, operationDto.type) &&
                Objects.equals(pan_mask, operationDto.type);
    }

    @Override
    public Long getId() {
        return id;
    }
}