package com.tw.api.json;

import com.tw.core.Player;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<LandJSON> getLands() {
        return player.getLands().stream().map(LandJSON::new).collect(Collectors.toList());
    }

    public LandJSON getCurrentPosition() {
        return new LandJSON(player.getCurrentPosition());
    }

    public String getUri() {
        return "/game/players/" + player.getId();
    }
}
