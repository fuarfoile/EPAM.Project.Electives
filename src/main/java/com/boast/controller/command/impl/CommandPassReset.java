package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.util.constant.Link;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.domain.User;
import com.boast.model.util.InputChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Комманда установления нового пароля аккауна с утраченым паролем*/
public class CommandPassReset implements Command {
    private static Logger logger = LogManager.getLogger(CommandPassReset.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        Locale locale = new Locale((String) session.getAttribute("language"));
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection = MySqlConnectionFactory.getInstance().getConnection();

        request.setAttribute("login", request.getParameter("login"));
        if (!request.getParameter("password").equals(request.getParameter("rPassword"))) {
            request.setAttribute("error_msg", resource.getString("password.reset.message.error.email"));
            return Link.PASS_RESET.getLink();
        }

        try {
            User user = daoFactory.getUserDao(connection).getByEmail(request.getParameter("login"));

            logger.debug("user.getPassRecoveryKey() = " + user.getPassRecoveryKey());
            logger.debug("request.getParameter(\"passRecoveryKey\") = " + request.getParameter("passRecoveryKey"));

            if (user.getPassRecoveryKey().equals(request.getParameter("passRecoveryKey")) &&
                    user.getPassRecoveryKey().length() > 0) {
                user.setPassword(request.getParameter("password"));
                user.setPassRecoveryKey("");

                if (InputChecker.check(user)) {
                    daoFactory.getUserDao(connection).update(user);
                    request.setAttribute("msg", resource.getString("password.reset.massage.success"));
                } else {
                    request.setAttribute("msg", resource.getString("error.input"));
                }
            } else {
                return Link.ERROR.getLink();
            }
        } catch (SQLException | NullPointerException e) {
            logger.error("passReset: " + e);
        }
        return Link.PASS_RESET.getLink();
    }
}
