package com.boast.controller.command.impl.redirect;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Комманда перенаправления на страницу профиля*/
public class CommandRProfile implements Command {
    private static Logger logger = LogManager.getLogger(CommandRProfile.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        Receiver receiver = new Receiver(request, response);

        return receiver.rProfile(null);
    }
}