package com.boast.command.impl;

import com.boast.command.Command;
import com.boast.command.Receiver;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

public class CommandCancelCourse implements Command {
    private static Logger logger = LogManager.getLogger(CommandCancelCourse.class);
    private Receiver receiver;

    public CommandCancelCourse(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        int courseId = Integer.parseInt(request.getParameter("cancelCourseId"));

        MySqlDaoFactory daoFactory = new MySqlDaoFactory();
        Connection connection;
        User user = (User) session.getAttribute("user");
        try {
            connection = daoFactory.getConnection();
            daoFactory.getMySqlStudentCourseDao(connection).deleteByIds(user.getId(), courseId);
        } catch (SQLException e) {
            logger.error("deleteByIds: " + e);
        }

        return receiver.rProfile(user);
    }
}
