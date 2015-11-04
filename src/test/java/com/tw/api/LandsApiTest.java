package com.tw.api;

import com.tw.core.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LandsApiTest extends JerseyTest {
    private Game game;
    private Player player;
    private GameMap map;

    @Override
    public void setUp() throws Exception {
        Land land1 = mock(Land.class);
        Land land2 = mock(RealEstate.class);
        when(land2.getId()).thenReturn(1);
        when(land2.getType()).thenReturn("RealEstate");

        map = mock(GameMap.class);
        when(map.getLands()).thenReturn(asList(land1, land2));

        game = mock(Game.class);
        when(game.getMap()).thenReturn(map).thenReturn(map);
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
    public void should_list_lands() throws Exception {
        Response response = target("/game/lands").request().get();
        assertThat(response.getStatus(), is(200));
        List list = response.readEntity(List.class);
        assertThat(list.size(), is(2));

        Map land = (Map) list.get(1);
        assertThat(land.get("id"), is(1));
        assertThat(land.get("type"), is("RealEstate"));
    }
}
