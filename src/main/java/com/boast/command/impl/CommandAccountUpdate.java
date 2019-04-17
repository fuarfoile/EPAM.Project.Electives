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

public class CommandAccountUpdate implements Command {
    private static Logger logger = LogManager.getLogger(CommandAccountUpdate.class);
    private Receiver receiver;

    public CommandAccountUpdate(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        User user = new User();
        User oldUser = (User) session.getAttribute("user");
        user.setId(oldUser.getId());
        user.setEmail(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        if (oldUser.getPosition() == Position.ADMIN) {
            user.setPosition(Position.ADMIN);
        } else {
            user.setPosition(Position.valueOf(request.getParameter("position")));
        }
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        String repPassword = request.getParameter("rPassword");

        String errMsg = "";
        boolean emailFound = false;

        if (user.getPassword().length() == 0 && repPassword.length() == 0) {
            user.setPassword(null);
        }else if (!user.getPassword().equals(repPassword)) {
            errMsg += "Passwords mismatch";
        }

        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        try {
            connection = daoFactory.getConnection();
            if (!user.getEmail().equalsIgnoreCase(oldUser.getEmail())) {
                emailFound = daoFactory.getMySqlLoginDao(connection).isEmailInBase(user.getEmail());
            }
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

            if (request.getCookies().length > 0) {
                receiver.addCookie(user.getEmail(), user.getPassword());
            }

            try {
                connection = daoFactory.getConnection();
                if (daoFactory.getUserDao(connection).update(user)) {
                    request.setAttribute("msg", "Account data updated");
                } else {
                    logger.error("MySqlUserDao.update fail");
                }
            } catch (SQLException e) {
                logger.error("accountUpdate: " + e);
            }
        }

        request.setAttribute("position", user.getPosition());
        request.setAttribute ("login", user.getEmail());
        request.setAttribute ("name", user.getName());
        request.setAttribute ("surname", user.getSurname());
        request.setAttribute ("phoneNumber", user.getPhoneNumber());
        request.setAttribute("error_msg", errMsg);

        return receiver.rAccountUpdate();
    }
}
