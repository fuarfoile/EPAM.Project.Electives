package com.boast.controller.command;

/** Менеджер команд*/
public class Client {

    public Command initCommand(TypeCommand typeCommand) {

        return typeCommand.getCommand();
    }
}
