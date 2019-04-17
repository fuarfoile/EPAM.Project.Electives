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

public class CommandPassReset implements Command {
    private static Logger logger = LogManager.getLogger(CommandPassReset.class);
    private Receiver receiver;

    public CommandPassReset(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;

        request.setAttribute("login", request.getParameter("login"));
        if (!request.getParameter("password").equals(request.getParameter("rPassword"))) {
            request.setAttribute("error_msg", "Passwords mismatch");
            return "/jsp/passreset.jsp";
        }

        try {
            connection = daoFactory.getConnection();
            User user = daoFactory.getUserDao(connection).getByEmail(request.getParameter("login"));
            if (user.getPassRecoveryKey().equals(request.getParameter("passRecoveryKey")) &&
                    user.getPassRecoveryKey().length() > 0) {
                user.setPassword(request.getParameter("password"));
                user.setPassRecoveryKey("");
                daoFactory.getUserDao(connection).update(user);
                request.setAttribute("msg", "Password changed");
            } else {
                request.setAttribute("error_msg", "Something goes wrong, try again later");
                return "/jsp/error.jsp";
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("passReset: " + e);
        }
        return "/jsp/passreset.jsp";
    }
}
