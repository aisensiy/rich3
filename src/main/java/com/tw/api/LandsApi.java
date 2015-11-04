package com.tw.api;

import com.tw.api.json.LandJSON;
import com.tw.core.Game;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static java.util.stream.Collectors.toList;

public class LandsApi {
    private Game game;

    public LandsApi(Game game) {

        this.game = game;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMap() {
        return Response.status(200).entity(game.getMap().getLands().stream().map(LandJSON::new).collect(toList())).build();
    }
}
