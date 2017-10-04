package com.boast.controller.command.impl;

import com.boast.controller.command.Command;
import com.boast.controller.command.Receiver;
import com.boast.controller.util.constant.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Комманда выхода из аккаунта*/
public class CommandLogout implements Command {
    private static Logger logger = LogManager.getLogger(CommandLogout.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);

        session.removeAttribute("user");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") || cookie.getName().equals("password")) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return Link.LOGIN.getLink();
    }
}
