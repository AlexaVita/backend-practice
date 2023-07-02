package com.practice.backend.model;

public class Sector {

    Long id;
    String name;
    boolean active;
    String signcode;
    boolean checkIp;
    String allowedIps;

    public Sector(Long sectorId, String name, boolean active, String signcode, boolean checkIp, String allowedIps) {
        this.id = sectorId;
        this.name = name;
        this.active = active;
        this.signcode = signcode;
        this.checkIp = checkIp;
        this.allowedIps = allowedIps;
    }

    public Sector() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long sectorId) {
        this.id = sectorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSigncode() {
        return signcode;
    }

    public void setSigncode(String signcode) {
        this.signcode = signcode;
    }

    public boolean isCheckIp() {
        return checkIp;
    }

    public void setCheckIp(boolean checkIp) {
        this.checkIp = checkIp;
    }

    public String getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(String allowedIps) {
        this.allowedIps = allowedIps;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "sectorId=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", signcode='" + signcode + '\'' +
                ", checkIp=" + checkIp +
                ", allowedIps='" + allowedIps + '\'' +
                '}';
    }

}
