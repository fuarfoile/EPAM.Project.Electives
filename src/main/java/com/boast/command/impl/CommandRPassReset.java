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

public class CommandRPassReset implements Command {
    private static Logger logger = LogManager.getLogger(CommandRPassReset.class);
    private Receiver receiver;

    public CommandRPassReset(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        logger.debug("rPassReset: " +
                "login = " + request.getParameter("login") +
                ", passRecoveryKey = " + request.getParameter("passRecoveryKey"));
        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        try {
            connection = daoFactory.getConnection();
            User user = daoFactory.getUserDao(connection).getByEmail(request.getParameter("login"));
            if (user.getPassRecoveryKey().equals(request.getParameter("passRecoveryKey")) &&
                    user.getPassRecoveryKey().length() > 0) {
                request.setAttribute("passRecoveryKey", request.getParameter("passRecoveryKey"));
                request.setAttribute("login", request.getParameter("login"));
                return "/jsp/passreset.jsp";
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("rPassReset: " + e);
        }
        request.setAttribute("error_msg", "Something goes wrong, try again later");
        return "/jsp/error.jsp";
    }
}
