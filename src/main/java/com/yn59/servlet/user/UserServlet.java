package com.yn59.servlet.user;

import com.alibaba.fastjson2.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.yn59.pojo.User;
import com.yn59.service.user.UserServiceImpl;
import com.yn59.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Objects;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        if (Objects.equals(method, "pwdmodify")) {
            this.pwdModify(req, resp);
        } else if (Objects.equals(method, "savepwd")) {
            this.updatePwd(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String newpassword = req.getParameter("newpassword");
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);

        if (user != null && !StringUtils.isNullOrEmpty(newpassword)) {
            UserServiceImpl userService = new UserServiceImpl();
            boolean flag = userService.updatePwd(user.getId(), newpassword);
            if (flag) {
                req.setAttribute("message", "修改密码成功，请退出，使用新密码登录");
                req.getSession().removeAttribute(Constants.USER_SESSION);
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            } else {
                req.setAttribute("message", "修改密码失败");
            }
        } else {
            if (user == null) {
                req.setAttribute("message", "请先登录");
            } else {
                req.setAttribute("message", "新密码有问题");
            }
        }
    }

    public void pwdModify(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        HashMap<String, String> resultMap = new HashMap<>();

        if (user == null) {
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {
            resultMap.put("result", "error");
        } else {
            if (user.getUserPassword().equals(oldpassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }

        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resp.getWriter().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
