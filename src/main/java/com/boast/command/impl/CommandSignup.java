package com.boast.command.impl;

import com.boast.command.Command;
import com.boast.command.Receiver;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.Position;
import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

public class CommandSignup implements Command {
    private static Logger logger = LogManager.getLogger(CommandSignup.class);
    private Receiver receiver;

    public CommandSignup(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

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
            errMsg += "Passwords mismatch";
        }

        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        try {
            connection = daoFactory.getConnection();
            emailFound = daoFactory.getMySqlLoginDao(connection).isEmailInBase(user.getEmail());
        } catch (SQLException e) {
            logger.error(e);
        }

        if (emailFound) {
            if (errMsg.length() > 0) {
                errMsg += "\n";
            }

            errMsg += "Account with entered e-mail already exist";
        }

        if (errMsg.length() == 0) {
            session.setAttribute("user", user);
            session.setAttribute("AdminPosition", Position.ADMIN);

            if (rememberMe) {
                receiver.addCookie(user.getEmail(), user.getPassword());
            }

            try {
                connection = daoFactory.getConnection();
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
