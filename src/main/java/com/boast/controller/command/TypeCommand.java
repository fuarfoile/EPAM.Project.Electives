package com.boast.controller.command;

import com.boast.controller.command.impl.*;
import com.boast.controller.command.impl.redirect.*;

/** Перечисление доступных команд*/
public enum TypeCommand {
    LOCALIZATION(new CommandLocalization()),
    LOGIN(new CommandLogin()),
    R_LOGIN(new CommandRLogin()),
    LOGOUT(new CommandLogout()),
    SIGNUP(new CommandSignup()),
    R_SIGNUP(new CommandRSignup()),
    FORGOT(new CommandForgot()),
    R_FORGOT(new CommandRForgot()),
    PASS_RESET(new CommandPassReset()),
    R_PASS_RESET(new CommandRPassReset()),
    COURSE_SEARCH(new CommandCourseSearch()),
    R_ACCOUNT_UPDATE(new CommandRAccountUpdate()),
    ACCOUNT_UPDATE(new CommandAccountUpdate()),
    R_PROFILE(new CommandRProfile()),
    CANCEL_COURSE(new CommandCancelCourse()),
    APPLY_COURSE(new CommandApplyCourse()),
    R_COURSE(new CommandRCourse()),
    MARK_CHANGE(new CommandMarkChange()),
    COURSE_CHANGE(new CommandCourseChange()),
    R_COURSE_CREATE(new CommandRCourseCreate()),
    COURSE_CREATE(new CommandCourseCreate());

    private Command command;

    private TypeCommand(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
