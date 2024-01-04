package com.yn59.service.user;

import com.yn59.pojo.User;

public interface UserService {
    public User login(String userCode, String password);
}
