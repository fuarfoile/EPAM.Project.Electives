package com.boast.command;

public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public String invokeCommand() {
        return command.execute();
    }
}
