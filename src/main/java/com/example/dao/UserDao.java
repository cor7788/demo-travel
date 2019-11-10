package com.example.dao;

import com.example.domain.User;

public interface UserDao {
    User getUserByUserName(String name);

    void insertUser(User user);

    boolean login(User user);
}
