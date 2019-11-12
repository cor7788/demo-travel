package com.example.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 提取公共的 BaseServlet，这样就可以实现每一个类对应一个 Servlet。
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 假设路径为：/travel/user/add
        String requestURI = req.getRequestURI();
        // 获取到的方法名为：/add
        String methodName = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        // 使用反射调用对应的方法
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将对象序列化为 json，写回客户端
     */
    public void writeValue(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), obj);
    }

    /**
     * 将对象序列化为 json 并返回。
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
