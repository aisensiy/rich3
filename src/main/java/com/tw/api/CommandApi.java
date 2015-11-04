package com.tw.api;

import com.tw.api.json.GameResponseJSON;
import com.tw.core.GameResponse;
import com.tw.core.GameResponseCommand;
import com.tw.core.Player;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CommandApi {
    private final Player player;
    private final String commandName;

    public CommandApi(Player player, String commandName) {

        this.player = player;
        this.commandName = commandName;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response execute() {
        GameResponse gameResponse = player.execute(commandName);
        if (gameResponse instanceof GameResponseCommand) {
            return Response.status(200)
                    .entity(new GameResponseJSON(gameResponse))
                    .header("uri", String.format("/game/players/%d/commands", player.getId()))
                    .build();
        } else {
            return Response.status(200).entity(new GameResponseJSON(gameResponse)).build();
        }
    }
}
