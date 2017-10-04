package com.boast.controller.command.impl.redirect;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Комманда перенаправления на страницу курса*/
public class CommandRCourse implements Command {
    private static Logger logger = LogManager.getLogger(CommandRCourse.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        Receiver receiver = new Receiver(request, response);

        int courseId = Integer.parseInt(request.getParameter("courseId"));

        return receiver.rCource(courseId);
    }
}
