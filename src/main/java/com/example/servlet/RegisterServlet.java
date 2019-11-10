package com.example.servlet;

import com.example.domain.ResultInfo;
import com.example.domain.User;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 0.初始化返回的对象
        ResultInfo resultInfo = new ResultInfo();

        // 1.判断验证码是否正确
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkCodeServer = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (!StringUtils.isEmpty(checkCodeServer) && checkCodeServer.equalsIgnoreCase(check)) {
            // 2.获取数据
            User user = new User();
            Map<String, String[]> pMap = request.getParameterMap();
            try {
                BeanUtils.populate(user, pMap);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            // 3.注册
            UserService userService = new UserServiceImpl();
            boolean flag = userService.register(user);
            resultInfo.setFlag(flag);
            if (!flag) resultInfo.setErrorMsg("注册失败");
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
        }

        // 4.返回响应
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
