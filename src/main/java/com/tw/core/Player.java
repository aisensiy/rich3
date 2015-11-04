package com.tw.core;

import java.util.List;

public interface Player {
    int getMoney();

    int getPoint();

    List<Land> getLands();

    Land getCurrentPosition();

    List<Command> getCommands();

    GameResponse execute(String command);

    int getId();
}
