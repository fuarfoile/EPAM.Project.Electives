package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Комманда поска по курсам*/
public class CommandCourseSearch implements Command {
    private static Logger logger = LogManager.getLogger(CommandCourseSearch.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        Receiver receiver = new Receiver(request, response);

        return receiver.rCourseSearch(null);
    }
}
