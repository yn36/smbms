package com.yn59.service.user;

import com.yn59.pojo.User;

public interface UserService {
    public User login(String userCode, String password);

    public boolean updatePwd(int id, String password);

    public int getUserCount(String userName, int userRole);
}
