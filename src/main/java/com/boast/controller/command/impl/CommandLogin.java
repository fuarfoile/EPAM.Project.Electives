package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.util.constant.Link;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.domain.Position;
import com.boast.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Комманда входа в аккаунт*/
public class CommandLogin implements Command {
    private static Logger logger = LogManager.getLogger(CommandLogin.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        Receiver receiver = new Receiver(request, response);

        Locale locale = new Locale((String) session.getAttribute("language"));
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

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

        if (login == null || password == null) {
            return Link.WELCOME.getLink();
        }

        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection;

        connection = MySqlConnectionFactory.getInstance().getConnection();
        User user = daoFactory.getLoginDao(connection).getUser(login, password);

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
            request.setAttribute ("error_msg", resource.getString("login.massage.error"));
            return Link.LOGIN.getLink();
        }
    }
}
