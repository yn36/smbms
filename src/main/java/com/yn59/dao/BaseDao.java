package com.yn59.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");

        Properties properties = new Properties();

        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    // 查询公共类
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    public static boolean closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean flag = true;

        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;
            } catch (SQLException e) {
                flag = false;
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                flag = false;
                e.printStackTrace();
            }
        }

        return flag;
    }

}
