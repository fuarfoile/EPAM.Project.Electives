package com.boast.controller.command.impl.redirect;

import com.boast.controller.command.Command;
import com.boast.controller.util.constant.Link;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

/** Комманда перенаправления на страницу установления нового пароля для аккаунта с утраченым паролем*/
public class CommandRPassReset implements Command {
    private static Logger logger = LogManager.getLogger(CommandRPassReset.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        logger.debug("rPassReset: " +
                "login = " + request.getParameter("login") +
                ", passRecoveryKey = " + request.getParameter("passRecoveryKey"));
        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection = MySqlConnectionFactory.getInstance().getConnection();
        try {
            User user = daoFactory.getUserDao(connection).getByEmail(request.getParameter("login"));
            if (user.getPassRecoveryKey().equals(request.getParameter("passRecoveryKey")) &&
                    user.getPassRecoveryKey().length() > 0) {
                request.setAttribute("passRecoveryKey", request.getParameter("passRecoveryKey"));
                request.setAttribute("login", request.getParameter("login"));
                return Link.PASS_RESET.getLink();
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("rPassReset: " + e);
        }

        return Link.ERROR.getLink();
    }
}
