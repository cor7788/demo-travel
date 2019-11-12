package com.example.dao;

import com.example.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
