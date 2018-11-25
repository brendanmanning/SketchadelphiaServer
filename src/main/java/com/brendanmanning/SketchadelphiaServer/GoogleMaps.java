package com.brendanmanning.SketchadelphiaServer;

/**
 * SketchadelphiaServer
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

import com.google.gson.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class GoogleMaps {

    private static JsonObject lastResponse = null;

    private static GoogleMaps ourInstance = new GoogleMaps();
    public static GoogleMaps getInstance() {
        return ourInstance;
    }
    private GoogleMaps() {}

    public Route getRoute(String from, String to) throws MalformedURLException, IOException {

        // Download the API request
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + from.replaceAll(" ", "%20") + "&destination=" + to.replaceAll(" ", "%20") + "&key=AIzaSyDYDNHo6dYglAi8TWqfyxfe_60yUEvQY3c";
        String result = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();

        JsonObject googleResponse = new JsonParser().parse(result).getAsJsonObject();
        lastResponse = googleResponse;

        JsonArray routes = googleResponse.getAsJsonArray("routes");
        JsonObject routeJson = routes.get(0).getAsJsonObject();

        return new Route(routeJson);

    }

    public JsonObject getLastResponse() {
        return lastResponse;
    }

}
