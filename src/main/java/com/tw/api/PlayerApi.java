package com.tw.api;

import com.tw.api.json.PlayerJSON;
import com.tw.core.Player;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class PlayerApi {
    private Player player;

    public PlayerApi(Player player) {
        this.player = player;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlayerJSON getPlayerInfo() {

        return new PlayerJSON(player);
    }
}
