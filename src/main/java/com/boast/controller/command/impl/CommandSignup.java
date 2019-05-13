package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.domain.Position;
import com.boast.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Комманда регистрации нового пользователя*/
public class CommandSignup implements Command {
    private static Logger logger = LogManager.getLogger(CommandSignup.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        Receiver receiver = new Receiver(request, response);

        //Locale locale = new Locale((String) session.getAttribute("language"));
        Locale locale = (Locale) session.getAttribute("language");
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        User user = new User();
        user.setEmail(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setPosition(Position.valueOf(request.getParameter("position")));
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        String repPassword = request.getParameter("rPassword");

        String errMsg = "";
        boolean emailFound = false;
        boolean rememberMe = request.getParameter("rememberMe") != null;

        if (!user.getPassword().equals(repPassword)) {
            errMsg += resource.getString("signup.message.error.password");
        }

        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection = MySqlConnectionFactory.getInstance().getConnection();

        emailFound = daoFactory.getLoginDao(connection).isEmailInBase(user.getEmail());


        if (emailFound) {
            if (errMsg.length() > 0) {
                errMsg += "\n";
            }

            errMsg += resource.getString("signup.message.error.email");
        }

        if (errMsg.length() == 0) {
            session.setAttribute("user", user);
            session.setAttribute("AdminPosition", Position.ADMIN);

            if (rememberMe) {
                receiver.addCookie(user.getEmail(), user.getPassword());
            }

            try {
                if (daoFactory.getUserDao(connection).create(user)) {
                    return receiver.rProfile(user);
                } else {
                    logger.error("MySqlUserDao.create fail");
                }
            } catch (SQLException e) {
                logger.error("signup: " + e);
            }
        }

        request.setAttribute("position", user.getPosition());
        request.setAttribute ("login", user.getEmail());
        request.setAttribute ("name", user.getName());
        request.setAttribute ("surname", user.getSurname());
        request.setAttribute ("phoneNumber", user.getPhoneNumber());
        request.setAttribute("error_msg", errMsg);
        return receiver.rSignup();
    }
}
