package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.util.constant.Link;
import com.boast.controller.util.constant.Values;
import com.boast.domain.Course;
import com.boast.domain.CourseStatus;
import com.boast.domain.StudentCourse;
import com.boast.model.dao.DaoFactory;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import com.boast.model.dao.impl.MySqlDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Комманда изменения данных курса. Используется администратором*/
public class CommandCourseChange implements Command {
    private static Logger logger = LogManager.getLogger(CommandCourseChange.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        Receiver receiver = new Receiver(request, response);

        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        String description = request.getParameter("description");
        String status = request.getParameter("status");

        try {
            DaoFactory daoFactory = MySqlDaoFactory.getInstance();
            Connection connection = MySqlConnectionFactory.getInstance().getConnection();

            Course course = daoFactory.getCourseDao(connection).getById(courseId);
            course.setDescription(description);
            course.setTeacherId(teacherId);
            if (status != null) {
                course.setStatus(CourseStatus.valueOf(status));
            }

            daoFactory.getCourseDao(connection).update(course);

            return receiver.rCource(course.getId());
        } catch (SQLException e) {
            logger.error("CourseDao update fail: " + e);
        }

        return Link.ERROR.getLink();
    }
}
