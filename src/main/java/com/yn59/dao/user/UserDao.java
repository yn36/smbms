package com.yn59.dao.user;

import com.yn59.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    public User getLoginUser(Connection connection, String userCode) throws SQLException;
}
