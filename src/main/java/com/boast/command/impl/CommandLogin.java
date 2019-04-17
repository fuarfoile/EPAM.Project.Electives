package com.boast.command.impl;

import com.boast.command.Command;
import com.boast.command.Receiver;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.Position;
import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

public class CommandLogin implements Command {
    private static Logger logger = LogManager.getLogger(CommandLogin.class);
    private Receiver receiver;

    public CommandLogin(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        boolean rememberMe = request.getParameter("rememberMe") != null;
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                if (login == null && cookie.getName().equals("login")) {
                    login = cookie.getValue();
                } else if (password == null && cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
        }

        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            user = daoFactory.getMySqlLoginDao(connection).getUser(login, password);
        } catch (SQLException e) {
            logger.error("login: " + e);
        }

        logger.debug("action LOGIN: log = " + login + ", pass = " + password + ", remember = " + rememberMe);

        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("AdminPosition", Position.ADMIN);

            if (rememberMe) {
                receiver.addCookie(login, password);
            }

            return receiver.rProfile(user);
        } else {
            request.setAttribute ("login", login);
            request.setAttribute ("error_msg", "E-mail or password is incorrect");
            return "/jsp/login.jsp";
        }
    }
}
