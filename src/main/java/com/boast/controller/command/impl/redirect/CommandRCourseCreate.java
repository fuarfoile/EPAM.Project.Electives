package com.boast.controller.command.impl.redirect;

import com.boast.controller.command.Command;
import com.boast.controller.util.constant.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Комманда перенаправления на страницу создания нового курса. Используется администратором*/
public class CommandRCourseCreate implements Command {
    private static Logger logger = LogManager.getLogger(CommandRCourseCreate.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        return Link.COURSE_CREATE.getLink();
    }
}
