package com.tw.api.json;

import com.tw.core.Land;

public class LandJSON {
    private Land land;

    public LandJSON(Land land) {

        this.land = land;
    }

    public int getId() {
        return land.getId();
    }

    public String getType() {
        return land.getType();
    }
}
