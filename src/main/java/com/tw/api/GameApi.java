package com.tw.api;

import com.tw.core.Game;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/game")
public class GameApi {
    @Inject
    private Game game;


    @Path("players")
    public PlayersApi getPlayers() {
        return new PlayersApi(game);
    }
}
