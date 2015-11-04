package com.tw.api.json;

import com.tw.core.Player;

public class PlayerJSON {
    private Player player;

    public PlayerJSON(Player player) {

        this.player = player;
    }

    public int getMoney() {
        return player.getMoney();
    }

    public int getPoint() {
        return player.getPoint();
    }
}
