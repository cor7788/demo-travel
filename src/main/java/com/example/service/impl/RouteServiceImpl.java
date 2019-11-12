package com.example.service.impl;

import com.example.dao.RouteDao;
import com.example.dao.impl.RouteDaoImpl;
import com.example.domain.PageBean;
import com.example.domain.Route;
import com.example.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> queryPage(int cid, int currentPage, int pageSize) {
        PageBean<Route> routePage = new PageBean<>();

        // 1.查询总条数
        int total = routeDao.getTotal(cid);
        routePage.setTotalCount(total);
        // 2.总页数
        double totalPage = Math.ceil(total * 1.0 / pageSize);
        routePage.setTotalPage((int) totalPage);
        // 3.当前页数, 一页的数据条数
        routePage.setCurrentPage(currentPage);
        routePage.setPageSize(pageSize);
        // 4.查询数据
        int start = pageSize * (currentPage - 1);
        List<Route> list = routeDao.queryPage(cid, start, pageSize);
        routePage.setList(list);
        return routePage;
    }
}
