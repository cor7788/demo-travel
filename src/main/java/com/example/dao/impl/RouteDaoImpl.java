package com.example.dao.impl;

import com.example.dao.RouteDao;
import com.example.domain.Route;
import com.example.utils.JdbcUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtil.getDataSource());

    @Override
    public int getTotal(int cid) {
        String sql = "select count(*) from tab_route where cid = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cid);
    }

    @Override
    public List<Route> queryPage(int cid, int start, int count) {
        String sql = "select * from tab_route where cid = ? limit ?,?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, start, count);
    }
}
