package com.boast.command.impl;

import com.boast.command.Command;
import com.boast.command.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandLocalization implements Command {
    private static Logger logger = LogManager.getLogger(CommandLocalization.class);
    private Receiver receiver;

    public CommandLocalization(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        String language = request.getParameter("language");
        if (language.equals("ENG")) {
            session.setAttribute("lang", "en");
            logger.info("en localization has been set");
        } else {
            session.setAttribute("lang", "uk");
            logger.info("uk localization has been set");
        }

        return "/login.jsp";
    }
}
