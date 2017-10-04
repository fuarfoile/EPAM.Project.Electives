package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.util.constant.Link;
import com.boast.domain.Course;
import com.boast.domain.CourseStatus;
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

/** Комманда создания нового курса. Используется администратором*/
public class CommandCourseCreate implements Command {
    private static Logger logger = LogManager.getLogger(CommandCourseCreate.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        Locale locale = new Locale((String) session.getAttribute("language"));
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        Course course = new Course();
        course.setName(request.getParameter("name"));
        course.setTeacherId(Integer.parseInt(request.getParameter("teacherId")));
        course.setDescription(request.getParameter("description"));
        course.setStatus(CourseStatus.valueOf(request.getParameter("status")));

        String errorMag = "";

        try {
            course.setMaxStudentsCount(Integer.parseInt(request.getParameter("maxStudentsCount")));
            if (course.getMaxStudentsCount() < 0) {
                throw new NumberFormatException();
            } else {
                request.setAttribute("maxStudentsCount", course.getMaxStudentsCount());
            }
        } catch (NumberFormatException e) {
            errorMag += resource.getString("course.create.error.size");
        }

        if (course.getName() != null && course.getName().length() > 0) {
            if (errorMag.length() == 0) {

                DaoFactory daoFactory = MySqlDaoFactory.getInstance();
                Connection connection = MySqlConnectionFactory.getInstance().getConnection();

                try {
                    daoFactory.getCourseDao(connection).create(course);
                    request.setAttribute("msg", resource.getString("course.create.created"));
                } catch (SQLException e) {
                    request.setAttribute("error_msg", resource.getString("course.create.error"));
                    logger.error("Can't create a course: " + e);
                }
            }
        } else {
            if (errorMag.length() > 0){
                errorMag += "\n";
            }
            errorMag += resource.getString("course.create.error.name");
        }

        request.setAttribute("name", course.getName());
        request.setAttribute("teacherId", course.getTeacherId());
        request.setAttribute("description", course.getDescription());
        request.setAttribute("status", course.getStatus());

        request.setAttribute("error_msg", errorMag);
        return Link.COURSE_CREATE.getLink();
    }
}
