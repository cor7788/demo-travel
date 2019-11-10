package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.dao.impl.UserDaoImpl;
import com.example.domain.User;
import com.example.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        // 查询用户是否已经存在
        User mUser = userDao.getUserByUserName(user.getUsername());
        if(mUser != null) {
            return false;
        } else {
            userDao.insertUser(user);
            return true;
        }
    }

    @Override
    public boolean login(User user) {
        return userDao.login(user);
    }
}
