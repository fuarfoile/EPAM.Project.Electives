package com.boast.command.impl;

import com.boast.command.Command;
import com.boast.command.Receiver;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

public class CommandForgot implements Command {
    private static Logger logger = LogManager.getLogger(CommandForgot.class);
    private Receiver receiver;

    public CommandForgot(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        String email = request.getParameter("login");

        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        User user = null;
        try {
            connection = daoFactory.getConnection();
            user = daoFactory.getUserDao(connection).getByEmail(email);
        } catch (SQLException e) {
            logger.error("rSignup: user getByEmail, " + e);
        }

        if (user != null) {
            String generatedKey = "generatedKey";
            //TODO Отправка e-mail с ссылкой для востановления пароля
            //Вариант ссылки: http://localhost:8080/controller?TypeCommand=R_PASS_RESET&login=test@test&passRecoveryKey=generatedKey
            user.setPassRecoveryKey(generatedKey);
            try {
                connection = daoFactory.getConnection();
                daoFactory.getUserDao(connection).update(user);
            } catch (SQLException e) {
                logger.error("rSignup: update user with passRecoveryKey, " + e);
            }
            request.setAttribute("msg", "Check your e-mail");
        } else {
            request.setAttribute ("login", email);
            request.setAttribute("error_msg", "Account with entered e-mail not found");
        }

        return "/jsp/forgot.jsp";
    }
}
