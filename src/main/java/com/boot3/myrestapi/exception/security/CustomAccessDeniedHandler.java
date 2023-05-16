package com.boot3.myrestapi.exception.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Set response code
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Set response content type to JSON
        response.setContentType("application/json;charset=UTF-8");

        // Create response content
        JSONObject obj = new JSONObject();
        obj.put("code", HttpServletResponse.SC_FORBIDDEN);
        obj.put("message", "Access Forbidden");

        // Add content to the response
        response.getWriter().write(obj.toString());

    }
}