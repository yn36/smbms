package com.yn59.servlet.user;

import com.yn59.pojo.User;
import com.yn59.service.user.UserServiceImpl;
import com.yn59.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("userCode"));

        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        if (user != null) {//登录成功
            //将用户的信息放到Session中
            req.getSession().setAttribute(Constants.USER_SESSION, user);
            //页面跳转（frame.jsp）
            resp.sendRedirect("jsp/frame.jsp");
        } else {
            req.setAttribute("error", "用户名或密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req, resp);//转发回登录页面，并提示用户名或密码错误
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
