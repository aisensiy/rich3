package com.tw.api.json;

import com.tw.core.GameResponse;

public class GameResponseJSON {
    private GameResponse gameResponse;

    public GameResponseJSON(GameResponse gameResponse) {
        this.gameResponse = gameResponse;
    }

    public String getMessage() {
        return gameResponse.getMessage();
    }
}
