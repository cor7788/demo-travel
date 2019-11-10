package com.example.service;

import com.example.domain.User;

public interface UserService {
    boolean register(User user);

    boolean login(User user);
}
