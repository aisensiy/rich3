package com.tw.core;

public interface Command {
    String getName();
    GameResponse execute(Player player);
}
