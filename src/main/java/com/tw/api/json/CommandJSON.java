package com.tw.api.json;

import com.tw.core.Command;

public class CommandJSON {
    private Command command;

    public CommandJSON(Command command) {
        this.command = command;
    }

    public String getName() {
        return command.getName();
    }

    public String getParameters() {
        return command.getParameters();
    }
}
