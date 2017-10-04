package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.util.constant.Link;
import com.boast.domain.Position;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import com.boast.domain.StudentCourse;
import com.boast.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

/** Комманда записи студанта на курс. Используется студентами*/
public class CommandApplyCourse implements Command {
    private static Logger logger = LogManager.getLogger(CommandApplyCourse.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        Receiver receiver = new Receiver(request, response);

        User user = (User) session.getAttribute("user");

        if (user.getPosition() == Position.STUDENT) {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            logger.debug(request.getParameter("courseId") + " " + courseId);

            MySqlDaoFactory daoFactory = MySqlDaoFactory.getInstance();
            Connection connection = MySqlConnectionFactory.getInstance().getConnection();

            try {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setCourseId(courseId);
                studentCourse.setStudentId(user.getId());

                if (daoFactory.getStudentCourseDao(connection).getByIds(user.getId(), courseId) == null) {
                    daoFactory.getStudentCourseDao(connection).create(studentCourse);
                }

                return receiver.rCource(courseId);
            } catch (SQLException e) {
                logger.error("create: " + e);
            }
        }

        return Link.ERROR.getLink();
    }
}
