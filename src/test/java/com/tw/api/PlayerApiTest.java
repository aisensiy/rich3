package com.tw.api;

import com.tw.core.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class PlayerApiTest extends JerseyTest {
    private Game game;
    private Player player;

    @Override
    public void setUp() throws Exception {
        game = mock(Game.class);
        player = mock(Player.class);
        super.setUp();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig().packages("com.tw.api").register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(game).to(Game.class);
            }
        });
    }

    @Test
    public void should_get_player_result_player_info() throws Exception {
        when(game.getPlayer(1)).thenReturn(player);
        when(player.getMoney()).thenReturn(1000);
        when(player.getPoint()).thenReturn(0);
        List<Land> lands = new ArrayList<>();
        when(player.getLands()).thenReturn(lands);
        Land land = mock(Land.class);
        when(land.getId()).thenReturn(1);
        when(player.getCurrentPosition()).thenReturn(land);

        Response response = target("/game/players/1").request().get();
        assertThat(response.getStatus(), is(200));
        Map map = response.readEntity(Map.class);
        assertThat(map.get("money"), is(1000));
        assertThat(map.get("point"), is(0));
    }

    @Test
    public void should_return_404_for_player_not_exists() throws Exception {
        when(game.getPlayer(1)).thenReturn(null);
        Response response = target("/game/players/1").request().get();
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_list_player_commands() throws Exception {
        when(game.getPlayer(1)).thenReturn(player);
        Command command = mock(Command.class);
        when(command.getName()).thenReturn("roll");
        when(player.getCommands()).thenReturn(asList(command));
        Response response = target("/game/players/1/commands").request().get();
        List list = response.readEntity(List.class);

        assertThat(response.getStatus(), is(200));
        assertThat(list.size(), is(1));
        assertThat(((Map) list.get(0)).get("name"), is("roll"));
    }

    @Test
    public void should_execute_command_and_return_message_with_response() throws Exception {
        when(game.getPlayer(1)).thenReturn(player);
        GameResponse gameResponse = new GameResponse() {
            @Override
            public String getMessage() {
                return "buy land success";
            }
        };
        Command buyLand = mock(Command.class);
        when(player.findCommandByName("buyLand")).thenReturn(buyLand);
        when(player.execute(buyLand)).thenReturn(gameResponse);
        Response response = target("/game/players/1/commands/buyLand").request().post(Entity.form(new Form()));

        assertThat(response.getStatus(), is(200));
        verify(player).execute(eq(buyLand));
        Map map = response.readEntity(Map.class);
        assertThat(map.get("message"), is("buy land success"));
    }

    @Test
     public void should_execute_command_and_return_uri_with_response_command() throws Exception {
        when(game.getPlayer(1)).thenReturn(player);
        GameResponse gameResponse = new GameResponseCommand() {

            @Override
            public String getMessage() {
                return "Do you want to buy this land? (Y/N)";
            }
        };
        Command roll = mock(Command.class);
        when(player.findCommandByName("roll")).thenReturn(roll);
        when(roll.execute(eq(player))).thenReturn(gameResponse);
        when(player.getId()).thenReturn(1);
        when(player.execute(roll)).thenReturn(gameResponse);
        Response response = target("/game/players/1/commands/roll").request().post(Entity.form(new Form()));

        verify(player).execute(eq(roll));
        assertThat(response.getStatus(), is(200));
        assertThat(response.getHeaders().get("uri").get(0), is("/game/players/1/commands"));
        Map map = response.readEntity(Map.class);
        assertThat(map.get("message"), is("Do you want to buy this land? (Y/N)"));
    }

    @Test
    public void should_return_404_if_command_not_found() throws Exception {
        when(game.getPlayer(1)).thenReturn(player);
        when(player.findCommandByName(eq("roll"))).thenReturn(null);
        Response response = target("/game/players/1/commands/roll").request().post(Entity.form(new Form()));
        assertThat(response.getStatus(), is(404));
    }
}
