package com.boast.command;

import com.boast.command.impl.*;

public class Client {
    private Receiver receiver;

    public Client(Receiver receiver) {
        this.receiver = receiver;
    }

    public Command initCommand(TypeCommand typeCommand) {
        Command command = null;

        switch (typeCommand) {
            case LOCALIZATION:
                command = new CommandLocalization(receiver);
                break;
            case LOGIN:
                command = new CommandLogin(receiver);
                break;
            case R_LOGIN:
                command = new CommandRLogin(receiver);
                break;
            case LOGOUT:
                command = new CommandLogout(receiver);
                break;
            case SIGNUP:
                command = new CommandSignup(receiver);
                break;
            case R_SIGNUP:
                command = new CommandRSignup(receiver);
                break;
            case FORGOT:
                command = new CommandForgot(receiver);
                break;
            case R_FORGOT:
                command = new CommandRForgot(receiver);
                break;
            case PASS_RESET:
                command = new CommandPassReset(receiver);
                break;
            case R_PASS_RESET:
                command = new CommandRPassReset(receiver);
                break;
            case COURSE_SEARCH:
                command = new CommandCourseSearch(receiver);
                break;
            case R_ACCOUNT_UPDATE:
                command = new CommandRAccountUpdate(receiver);
                break;
            case ACCOUNT_UPDATE:
                command = new CommandAccountUpdate(receiver);
                break;
            case R_PROFILE:
                command = new CommandRProfile(receiver);
                break;
            case CANCEL_COURSE:
                command = new CommandCancelCourse(receiver);
                break;
            case APPLY_COURSE:
                command = new CommandApplyCourse(receiver);
                break;
        }

        return command;
    }
}
