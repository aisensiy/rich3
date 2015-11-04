package com.tw.api;

import com.tw.api.json.CommandJSON;
import com.tw.core.Player;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static java.util.stream.Collectors.toList;

public class CommandsApi {
    private Player player;

    public CommandsApi(Player player) {
        this.player = player;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(200).entity(player.getCommands().stream().map(CommandJSON::new).collect(toList())).build();
    }

    @Path("{commandName}")
    public CommandApi getCommand(@PathParam("commandName") String commandName) {
        return new CommandApi(player, commandName);
    }
}
