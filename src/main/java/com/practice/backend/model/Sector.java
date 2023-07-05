package com.practice.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

public class Sector implements IGuapEntity {
    @NotNull
    private Long id;
    @Pattern(regexp = "[\\p{Print}]{0,255}")
    private String name;
    private Boolean active;
    @Pattern(regexp = "[\\p{Print}]{0,255}")
    private String signCode;
    private Boolean checkIp;
    //@Pattern(regexp = "^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\.(?!$)|$)){4}$")
    private String allowedIps;

    public Sector(Long id, String name, Boolean active, String signCode, Boolean checkIp, String allowedIps) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.signCode = signCode;
        this.checkIp = checkIp;
        this.allowedIps = allowedIps;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSectorId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public Boolean getCheckIp() {
        return checkIp;
    }

    public void setCheckIp(Boolean checkIp) {
        this.checkIp = checkIp;
    }

    public String getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(String allowedIps) {
        this.allowedIps = allowedIps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sectorDto = (Sector) o;
        return Objects.equals(id, sectorDto.id) &&
                Objects.equals(name, sectorDto.name) &&
                Objects.equals(active, sectorDto.active) &&
                Objects.equals(signCode, sectorDto.signCode) &&
                Objects.equals(checkIp, sectorDto.checkIp) &&
                Objects.equals(allowedIps, sectorDto.allowedIps);
    }

    @Override
    public String toString() {
        return "SectorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", checkIp=" + checkIp +
                ", allowedIps='" + allowedIps + '\'' +
                '}';
    }
}
