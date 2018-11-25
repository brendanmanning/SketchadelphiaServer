package com.brendanmanning.PhillyDillyDilly;

import com.google.gson.JsonObject;

/**
 * PDD
 * <p>
 * Copyright 2018 Brendan Manning. All rights reserved.
 */


public class Coordinates
{

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return this.lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    double lat;


    public double getLon() {
        return this.lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    double lon;

    @Override
    public String toString() {
        return "(" + this.lat + "," + this.lon + ")";
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lat", this.lat);
        jsonObject.addProperty("lon", this.lon);
        return jsonObject;
    }
}