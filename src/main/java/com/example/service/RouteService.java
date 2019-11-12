package com.example.service;

import com.example.domain.PageBean;
import com.example.domain.Route;

public interface RouteService {

    PageBean<Route> queryPage(int cid, int currentPage, int pageSize);
}
