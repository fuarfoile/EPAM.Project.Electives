package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.util.constant.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Комманда изменения языка страниц*/
public class CommandLocalization implements Command {
    private static Logger logger = LogManager.getLogger(CommandLocalization.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        String language = request.getParameter("language");
        if (language.equals("en")) {
            session.setAttribute("language", "en");
            logger.info("en localization has been set");
        } else {
            session.setAttribute("language", "ru");
            logger.info("ru localization has been set");
        }

        return Link.LOGIN.getLink();
    }
}
