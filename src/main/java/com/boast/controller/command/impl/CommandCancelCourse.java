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

/** Комманда отмены регистрации студента на курс. Используется студентами*/
public class CommandCancelCourse implements Command {
    private static Logger logger = LogManager.getLogger(CommandCancelCourse.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        Receiver receiver = new Receiver(request, response);

        int courseId = Integer.parseInt(request.getParameter("courseId"));

        MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
        Connection connection;
        User user = (User) session.getAttribute("user");
        try {
            connection = MySqlConnectionFactory.getInstance().getConnection();
            daoFactory.getStudentCourseDao(connection).deleteByIds(user.getId(), courseId);

            return receiver.rCource(courseId);
        } catch (SQLException e) {
            logger.error("deleteByIds: " + e);
        }

        return Link.ERROR.getLink();
    }
}
