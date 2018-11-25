package com.brendanmanning.SketchadelphiaServer;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

import com.brendanmanning.PhillyDillyDilly.Grid;
import com.google.gson.*;

import java.util.HashSet;
import java.util.Iterator;

public class Route {

    JsonObject routeJson = null;

    Leg[] legs = null;
    HashSet<Grid> grids = new HashSet<Grid>();

    public Route(JsonObject routeJson) {

        routeJson = routeJson;
        JsonArray routeLegs = routeJson.getAsJsonArray("legs");

        legs = new Leg[routeLegs.size()];
        for(int l = 0; l < routeLegs.size(); l++) {
            legs[l] = new Leg(routeLegs.get(l).getAsJsonObject());
            grids.addAll(legs[l].gridsCrossed());
        }

    }

    public int numberOfIncidents() {
        int reportedIncidents = 0;
        for(int i = 0; i < legs.length; i++) {
            reportedIncidents += legs[i].numberOfIncidents();
        }
        return reportedIncidents;
    }

    public Grid[] getGrids() {
        return grids.toArray(new Grid[]{});
    }

    // Used only for testing
    public boolean hasDuplicatesInHashSet() {


        HashSet<Grid> grids = new HashSet<Grid>();
        for(int l = 0; l < legs.length; l++) {
            Iterator<Grid> iterator = legs[l].gridsCrossed().iterator();
            while(iterator.hasNext()) {
                grids.add(iterator.next());
            }
        }

        Grid[] gridArray = new Grid[grids.size()];
        int i = 0;
        Iterator<Grid> iterator = grids.iterator();
        while(iterator.hasNext()) {
            gridArray[i] = iterator.next();
            i++;
        }

        for(int j = 0; j < gridArray.length; j++) {
            for(int k = 0; k < gridArray.length; k++) {
                if(j != k && gridArray[j].getTopLeft().getLat() == gridArray[k].getTopLeft().getLat() && gridArray[j].getTopLeft().getLon() == gridArray[k].getTopLeft().getLon()) {
                    return true;
                }
            }
        }
        return false;
    }

}
