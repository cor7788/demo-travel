package com.example.dao;

import com.example.domain.Route;

import java.util.List;

public interface RouteDao {
    int getTotal(int cid);
    List<Route> queryPage(int cid, int start, int count);
}
