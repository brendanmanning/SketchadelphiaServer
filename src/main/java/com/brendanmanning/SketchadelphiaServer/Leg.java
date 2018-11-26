package com.brendanmanning.SketchadelphiaServer;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

import com.brendanmanning.PhillyDillyDilly.*;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Leg {

    private PolylineDecoder decoder = new PolylineDecoder();

    private JsonObject legJson = null;

    private HashSet<Grid> grids = new HashSet<Grid>();
    private boolean hasMatchedGrids = false;

    private int reportedIncidents = 0;

    public Leg(JsonObject legJson) {
        this.legJson = legJson;

        // Find the coordinates that we cross through
        JsonArray steps = this.legJson.getAsJsonArray("steps");
        List<Coordinates> coordinates = new ArrayList<Coordinates>();
        for(int s = 0; s < steps.size(); s++) {

            // Sort throug the JSON to get the "points" string (encoded coordinates)
            JsonObject step = steps.get(s).getAsJsonObject();
            JsonObject polyline = step.getAsJsonObject("polyline");
            String pointsString = polyline.get("points").getAsString().replaceAll("(\\\\\\\\)+", "\\\\");

            // Add every decoded coordinate to a list of coordinates this leg passes through
            List<Coordinates> thesecoordinates = decoder.decode(pointsString);
            for(int i = 0; i < thesecoordinates.size(); i++) {
                coordinates.add(thesecoordinates.get(i));
            }
        }

        // Place each coordinate pair in a grid
        for(int i = 0; i < coordinates.size(); i++) {
            try {
                int[] index = GridGenerator.getInstance().getGridIndex(coordinates.get(i));
                Grid grid = com.brendanmanning.PhillyDillyDilly.Main.getGrids()[index[0]][index[1]];
                grids.add(grid);
                reportedIncidents += grid.numberOfIncidents();
            } catch (GridNotContainsPointException gncpe) {
                //System.out.println("\t+ Block not contained in Grid ");
            }
        }

    }

    public HashSet<Grid> gridsCrossed() {
        return grids;
    }

    public int numberOfIncidents() {
        return reportedIncidents;
    }

}
