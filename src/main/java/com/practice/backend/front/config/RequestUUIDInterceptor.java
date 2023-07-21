package com.practice.backend.front.config;

import com.practice.backend.front.user.User;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@EnableAspectJAutoProxy
public class RequestUUIDInterceptor implements HandlerInterceptor {

    Map<User, UUID> users;

    RequestUUIDInterceptor() {
        users = new HashMap<>();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Получаем id сессии
        String requestSessionId = request.getRequestedSessionId();
        // Получаем Ip адрес пользователя
        String remoteIpAddress = request.getRemoteAddr();

        // Генерируем пользователя на основе полученной информации
        User user = new User(requestSessionId, remoteIpAddress);

        // Получаем UUID из HashMap
        // equals переписан таким образом, что пользователи считаются одинаковыми, если у них одинаковая сессия, или одинаковы Ip
        UUID uuid = users.get(user);

        if (uuid == null) {
            // Генерируем новый id и добавляем пользователя в HashMap, если его там не было
            uuid = UUID.randomUUID();
            users.put(user, uuid);
        }

        request.setAttribute("uuid", uuid);

        return true;
    }

}
