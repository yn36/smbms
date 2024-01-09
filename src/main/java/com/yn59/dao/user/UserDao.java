package com.yn59.dao.user;

import com.yn59.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    public User getLoginUser(Connection connection, String userCode, String password) throws SQLException;

    public int updatePwd(Connection connection, int id, String password) throws SQLException;

    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException;
}
