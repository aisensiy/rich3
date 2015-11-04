package com.tw.api;

import com.tw.core.Game;
import com.tw.core.Player;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class PlayersApi {
    private Game game;

    public PlayersApi(Game game) {
        this.game = game;
    }

    @Path("/{playerId}")
    public PlayerApi getPlayer(@PathParam("playerId") int playerId) {
        Player player = game.getPlayer(playerId);
        return new PlayerApi(player);
    }

}
