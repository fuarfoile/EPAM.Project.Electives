package com.boast.command.impl;

import com.boast.command.Command;
import com.boast.command.Receiver;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.StudentCourse;
import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

public class CommandApplyCourse implements Command {
    private static Logger logger = LogManager.getLogger(CommandApplyCourse.class);
    private Receiver receiver;

    public CommandApplyCourse(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        int courseId = Integer.parseInt(request.getParameter("applyCourseId"));
        logger.debug(request.getParameter("applyCourseId") + " " + courseId);

        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        User user = (User) session.getAttribute("user");
        try {
            connection = daoFactory.getConnection();
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setCourseId(courseId);
            studentCourse.setStudentId(user.getId());
            daoFactory.getMySqlStudentCourseDao(connection).create(studentCourse);
        } catch (SQLException e) {
            logger.error("create: " + e);
        }

        return receiver.rCourseSearch(null);
    }
}
