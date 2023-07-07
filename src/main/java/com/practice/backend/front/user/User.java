package com.practice.backend.front.user;

import java.util.Objects;

/** Чтобы отслеживать путь пользователя в логах */
public class User {

    private String sessionId;

    private String currentIp;

    public User(String sessionId, String currentIp) {
        this.sessionId = sessionId;
        this.currentIp = currentIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getCurrentIp() {
        return currentIp;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setCurrentIp(String currentIp) {
        this.currentIp = currentIp;
    }

    /** Пользователи считаются одинаковыми, если у них одинаковая сессия, или одинаковы Ip */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(sessionId, user.sessionId) || Objects.equals(currentIp, user.currentIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, currentIp);
    }
}
