package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.exception.MarkException;
import com.boast.controller.util.constant.Link;
import com.boast.controller.util.constant.Values;
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
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Комманда изменения оценки и/или отзыва студенту. Используется преподавателем курса*/
public class CommandMarkChange implements Command {
    private static Logger logger = LogManager.getLogger(CommandMarkChange.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        Receiver receiver = new Receiver(request, response);

        Locale locale = new Locale((String) session.getAttribute("language"));
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        int studentCourseId = Integer.parseInt(request.getParameter("studentCourseId"));

        int newMak = -1;
        try {
            newMak = Integer.parseInt(request.getParameter("mark"));
        } catch (NumberFormatException e) {
            logger.debug("incorrect input for mark value: " + e);
        }

        try {
            DaoFactory daoFactory = MySqlDaoFactory.getInstance();
            Connection connection = MySqlConnectionFactory.getInstance().getConnection();

            StudentCourse studentCourse = daoFactory.getStudentCourseDao(connection).getById(studentCourseId);

            logger.debug("resource Locale = " + resource.getLocale());

            try {
                studentCourse.setMark(newMak);
                studentCourse.setReview(request.getParameter("review"));

                daoFactory.getStudentCourseDao(connection).update(studentCourse);
            } catch (MarkException e){
                logger.debug("Incorrect input for mark value: " + e);
                request.setAttribute("msg", resource.getString("course.msg.error.mark"));
            }

            return receiver.rCource(studentCourse.getCourseId());
        } catch (SQLException e) {
            logger.error("StudentCourseDao update fail: " + e);
        }

        return Link.ERROR.getLink();
    }
}
