package com.tw.api;

import com.tw.api.json.PlayerJSON;
import com.tw.core.Player;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PlayerApi {
    private Player player;

    public PlayerApi(Player player) {
        this.player = player;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerInfo() {
        if (player == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(new PlayerJSON(player)).build();
    }

    @Path("commands")
    public CommandsApi getCommands() {
        return new CommandsApi(player);
    }
}
