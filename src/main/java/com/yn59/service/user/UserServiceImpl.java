package com.yn59.service.user;

import com.yn59.dao.BaseDao;
import com.yn59.dao.user.UserDao;
import com.yn59.dao.user.UserDaoImpl;
import com.yn59.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }


    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        connection = BaseDao.getConnection();
        try {
            user = userDao.getLoginUser(connection, userCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(connection, null, null);
        }

        return user;
    }

    @Test()
    public void test() {
        User admin = new UserServiceImpl().login("admin", "1234567");
        System.out.println(admin.getUserName());
    }
}
