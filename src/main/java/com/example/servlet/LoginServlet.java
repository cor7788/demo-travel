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

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置响应信息
        ResultInfo resultInfo = new ResultInfo();

        // 1.判断验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkCodeServer = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if(!StringUtils.isEmpty(checkCodeServer) && checkCodeServer.equalsIgnoreCase(check)) {
            // 2.判断用户名和密码
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, map);
                UserService userService = new UserServiceImpl();
                boolean flag = userService.login(user);
                resultInfo.setFlag(flag);
                if(!flag) resultInfo.setErrorMsg("用户名或密码错误");
                if(flag) request.getSession().setAttribute("user", user);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
        }

        // 返回响应
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), resultInfo);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
