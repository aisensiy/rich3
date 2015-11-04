package com.tw.api;

import com.tw.core.Game;
import com.tw.core.Land;
import com.tw.core.Player;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}
