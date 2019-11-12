package com.example.dao.impl;

import com.example.dao.CategoryDao;
import com.example.domain.Category;
import com.example.utils.JdbcUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtil.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }
}
