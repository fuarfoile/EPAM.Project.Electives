package com.boast.command.impl;

import com.boast.command.Command;
import com.boast.command.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandRProfile implements Command {
    private static Logger logger = LogManager.getLogger(CommandRProfile.class);
    private Receiver receiver;

    public CommandRProfile(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(){
        HttpServletRequest request = receiver.getRequest();
        HttpServletResponse response = receiver.getResponse();
        HttpSession session = receiver.getSession();

        return receiver.rProfile(null);
    }
}
