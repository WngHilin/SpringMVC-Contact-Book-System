package com.example.demo.handler;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String name = (String)request.getSession().getAttribute("User");
            if(name != null)
                return true;
            else
                response.sendRedirect("/login");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
