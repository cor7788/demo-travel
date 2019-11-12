package com.example.service.impl;

import com.example.dao.CategoryDao;
import com.example.dao.impl.CategoryDaoImpl;
import com.example.domain.Category;
import com.example.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
