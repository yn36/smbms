package com.yn59.dao.user;

import com.yn59.dao.BaseDao;
import com.yn59.pojo.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    @Override
    public User getLoginUser(Connection connection, String userCode, String password) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from user where userCode=? and userPassword=?";
            Object[] params = {userCode, password};
            rs = BaseDao.execute(connection, pstmt, sql, params);

            if (rs.next()) {
                user = new User();

                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getDate("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getDate("modifyDate"));
            }
            BaseDao.closeResource(null, pstmt, rs);
        }

        return user;
    }

    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstmt = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "update user set userPassword=? where id=?";
            Object[] params = {password, id};
            updateRows = BaseDao.executeUpdate(connection, pstmt, sql, params);
            BaseDao.closeResource(null, pstmt, null);
        }

        return updateRows;
    }
}
