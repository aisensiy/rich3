package com.tw.core;

import java.util.List;

public interface Player {
    int getMoney();

    int getPoint();

    List<Land> getLands();

    Land getCurrentPosition();

    List<Command> getCommands();

    GameResponse execute(Command command);

    int getId();

    Command findCommandByName(String commandName);
}
