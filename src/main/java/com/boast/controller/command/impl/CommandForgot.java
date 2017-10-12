package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.util.constant.Link;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
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

/** Комманда генерации и отправки ссылки для востановления пароля на указаный e-mail*/
public class CommandForgot implements Command {
    private static Logger logger = LogManager.getLogger(CommandForgot.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        Locale locale = new Locale((String) session.getAttribute("language"));
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        String email = request.getParameter("login");

        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection = MySqlConnectionFactory.getInstance().getConnection();
        User user = null;
        try {
            user = daoFactory.getUserDao(connection).getByEmail(email);
        } catch (SQLException e) {
            logger.error("Can't get user from db: getByEmail. " + e);
        }

        if (user != null) {
            String generatedKey = "generatedKey";
            //TODO Отправка e-mail с ссылкой для востановления пароля
            //Вариант ссылки: http://localhost:8080/controller?TypeCommand=R_PASS_RESET&login=test@test&passRecoveryKey=generatedKey
            user.setPassRecoveryKey(generatedKey);
            daoFactory.getUserDao(connection).update(user);

            request.setAttribute("msg", resource.getString("forgot.message.success"));
        } else {
            request.setAttribute ("login", email);
            request.setAttribute("error_msg", resource.getString("forgot.message.error.email"));
        }

        return Link.FORGOT.getLink();
    }
}
