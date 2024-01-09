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
            user = userDao.getLoginUser(connection, userCode, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(connection, null, null);
        }

        return user;
    }

    @Override
    public boolean updatePwd(int id, String password) {
        Connection connection = null;
        boolean flag = false;

        connection = BaseDao.getConnection();
        try {
            int i = userDao.updatePwd(connection, id, password);
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String userName, int userRole) {
        Connection connection = null;
        int count = 0;

        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, userName, userRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return count;
    }

    @Test()
    public void test() {
        User admin = new UserServiceImpl().login("admin", "1234567");
        System.out.println(admin.getUserName());
    }

    @Test()
    public void test2() {
        boolean flag = new UserServiceImpl().updatePwd(1, "1234567");
        System.out.println(flag);
    }

    @Test()
    public void test3() {
        int count = new UserServiceImpl().getUserCount("", 1);
        System.out.println(count);
    }
}
