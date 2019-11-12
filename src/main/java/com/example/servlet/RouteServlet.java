package com.example.servlet;

import com.example.domain.PageBean;
import com.example.domain.Route;
import com.example.service.RouteService;
import com.example.service.impl.RouteServiceImpl;
import org.springframework.util.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "routeServlet", value = "/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    public void queryPage(HttpServletRequest req, HttpServletResponse resp) {

        // 从查询参数中获取变量并转换为 int 类型
        int currentPage = 1;
        int pageSize = 5;
        int cid = 0;

        String currentPageStr = req.getParameter("currentPage");
        if(!StringUtils.isEmpty(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
        }

        String pageSizeStr = req.getParameter("pageSize");
        if(!StringUtils.isEmpty(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        String cidStr = req.getParameter("cid");
        if(!StringUtils.isEmpty(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        // 根据条件查询数据
        PageBean<Route> pageRoute = routeService.queryPage(cid, currentPage, pageSize);
        try {
            writeValue(pageRoute, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
