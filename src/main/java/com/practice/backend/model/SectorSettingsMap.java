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


    public SectorSettingsMap(Long id, Long sectorId, String name, String value) {
        this.id = id;
        this.sectorId = sectorId;
        this.name = name;
        this.value = value;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectorSettingsMap that = (SectorSettingsMap) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sectorId, that.sectorId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return "SectorSettingsMap{" +
                "id=" + id +
                ", sectorId=" + sectorId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
        }
    }
