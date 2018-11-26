package com.brendanmanning.PhillyDillyDilly;

import com.brendanmanning.PhillyDillyDillyHelpers.PDDMath;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class GridGenerator {

    private static GridGenerator ourInstance = new GridGenerator();
    public static GridGenerator getInstance() {
        return ourInstance;
    }

    private static int GRIDS_EW = PDDConfig.getInstance().GRIDS_EW;
    private static int GRIDS_NS = PDDConfig.getInstance().GRIDS_NS;

    Coordinates extremeEast = null;
    Coordinates extremeWest = null;
    Coordinates extremeNorth = null;
    Coordinates extremeSouth = null;

    double coordinateDistanceEW = 0;
    double coordinateDistanceNS = 0;

    double blockHeight = 0;
    double blockWidth = 0;

    private int emptyGrids = 0;
    private int averageIncidentsPerGrid = -1;
    private int maxIncidentsInAGrid = -1;

    private static Percentile[] percentiles = null;

    public int getGRIDS_EW() {
        return this.GRIDS_EW;
    }

    public int getGRIDS_NS() {
        return this.GRIDS_NS;
    }

    public double getBlockHeight() {
        return blockHeight;
    }

    public double getBlockWidth() {
        return blockWidth;
    }

    public int getAverageIncidentsPerGrid() { return averageIncidentsPerGrid; }

    public int getMaxIncidentsInAGrid() { return maxIncidentsInAGrid; }

    /**
     * Creates a 2D array of grids from the given incidents
     * Typically CALLED ONLY ONCE in PhillyDillyDilly.Main
     *
     * As such, this method acts as a sort of "Constructor" for the GridGenerator singleton.
     *
     */
    public Grid[][] getGrids(List<Incident> incidents) {


        Grid[][] grids = new Grid[GRIDS_NS][GRIDS_EW];

        // Compute the max boundaries which we must enclose by finding the
        //  top left, top right, bottom left, and bottom right coordinates
        for(int i = 0; i < incidents.size(); i++) {
            Coordinates coordinates = incidents.get(i).getCoordinates();

            if(extremeNorth == null)
                extremeNorth = coordinates;
            if(extremeSouth == null)
                extremeSouth = coordinates;
            if(extremeEast == null)
                extremeEast = coordinates;
            if(extremeWest == null)
                extremeWest = coordinates;
            if(coordinates.getLat() > extremeNorth.getLat())
                extremeNorth = coordinates;
            if(coordinates.getLat() < extremeSouth.getLat())
                extremeSouth = coordinates;
            if(coordinates.getLon() > extremeEast.getLon())
                extremeEast = coordinates;
            if(coordinates.getLon() < extremeWest.getLon())
                extremeWest = coordinates;
        }

        // Calculate the coordinate offset applied to each successive grid
        // 73.000 -> 73.015 -> 73.030 with with each movement right

        //System.out.print("Extreme north: ");
        //System.out.println(extremeNorth);
        //System.out.print("Extreme south: ");
        //System.out.println(extremeSouth);

        coordinateDistanceNS = Math.abs(extremeNorth.getLat() - extremeSouth.getLat());
        coordinateDistanceEW = Math.abs(extremeEast.getLon() - extremeWest.getLon());
        blockHeight = coordinateDistanceNS / GRIDS_NS;
        blockWidth = coordinateDistanceEW / GRIDS_EW;

        // Debug
        //System.out.println("Extreme North: " + extremeNorth);
        //System.out.println("Extreme South: " + extremeSouth);
        //System.out.println("Extreme East: " + extremeEast);
        //System.out.println("Extreme West: " + extremeWest);

        //System.out.println();

        //System.out.println("Block Coordinate Size NS: " + blockHeight);
        //System.out.println("Block Coordinate Size EW: " + blockWidth);

        // Generate the grids in memory
        for(int y = 0; y < GRIDS_NS; y++) {
            for(int x = 0; x < GRIDS_EW; x++) {
                Coordinates coordinates = new Coordinates(
                        extremeNorth.getLat() - ( blockHeight * (y)),
                        extremeWest.getLon() + ( blockWidth * (x))
                );

                if(y==0 && x==0 || y == 0 && x == 1 || y == 1 && x ==0) {
                    System.out.println(coordinates);
                    System.out.println("\t" + y + "," + x);
                    System.out.println("\t" + getGridIndex(coordinates)[0] + "," + getGridIndex(coordinates)[1]);
                }

                grids[y][x] = new Grid(coordinates, blockHeight, blockWidth);
            }
        }

        boolean failed = false;

        // Match each incident to a grid, and iterate that grid's crime counter
        List<Incident> badIncidents = new ArrayList<Incident>();
        for(int i = 0; i < incidents.size(); i++) {
            Incident incident = incidents.get(i);

            int[] index = getGridIndex(incident.getCoordinates());

            int y_index = index[0];
            int x_index = index[1];


           // System.out.println(y_index);
           // System.out.println(x_index);
            try {
                grids[y_index][x_index].addIncident(incident);
                if(!grids[y_index][x_index].contains(incident)) {
                    System.out.println("FAILURE");
                    failed = true;
                }

            } catch (Exception e) {
                badIncidents.add(incident);
            }
        }

        // Calculate the number of grids with no incidents reported (those outside philadelphia city limits)
        emptyGrids = 0;
        int maxInAGrid = 0;
        for(int y = 0; y < grids.length; y++) {
            for (int x = 0; x < grids[y].length; x++) {
                if (grids[y][x].numberOfIncidents() == 0) {
                    emptyGrids++;
                }
                if (grids[y][x].numberOfIncidents() > maxInAGrid) {
                    emptyGrids = grids[y][x].numberOfIncidents();
                }
            }
        }

        // Calculate the average number of incidents per grid
        averageIncidentsPerGrid = ( incidents.size() - badIncidents.size() ) / ( (getGRIDS_EW() * getGRIDS_NS()) - emptyGrids );
        maxIncidentsInAGrid = maxInAGrid;

        // Calculate the 10th-90th percentile
        percentiles = generatePercentiles(grids, new int[]{10,20,30,40,50,60,70,80,90});


        if(failed) {
            System.exit(0);
        }

        Main.generateMap(new Coordinates[]{extremeWest,extremeSouth,extremeNorth,extremeEast}, "Extremes");

        Main.generateMap(badIncidents, "UnplaceableIncidents");

        // Debug the number of crimes in each grid
        /*for(int y = 0; y < GRIDS_NS; y++) {
            for (int x = 0; x < GRIDS_EW; x++) {
                System.out.println("Grid " + y + "," + x + ": " + grids[y][x].numberOfIncidents() + " reported incidents.");
                Main.generateMap(grids[y][x].getIncidents(), grids[y][x], "Grid_" + y + "-" + x);
            }
        }*/

        return grids;
    }

    public void generateGridFiles(List<Incident> incidents, File outputFolder) throws IOException {

        if(!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        Grid[][] grids = getGrids(incidents);

        for(int i = 0; i < grids.length; i++) {
            for(int j = 0; j < grids[i].length; j++) {
                File file = new File(outputFolder + File.separator + i + "," + j + ".json");
                file.createNewFile();

                JsonObject index = new JsonObject();
                index.addProperty("GRID_NS", i);
                index.addProperty("GRID_EW", j);

                JsonObject jsonObject = grids[i][j].toJson();
                jsonObject.add("index", index);

                FileUtils.writeStringToFile(file, jsonObject.toString());
            }
        }

    }

    public int[] getGridIndex(Coordinates coordinates) throws GridNotGeneratedException, GridNotContainsPointException{

        // Must call getGrids() first to ensure there ARE extremeCardinal variables
        if(extremeEast == null || extremeNorth == null || extremeSouth == null || extremeWest == null || blockHeight == 0 || blockWidth == 0 || coordinateDistanceEW == 0 || coordinateDistanceNS == 0)
            throw new GridNotGeneratedException("You must run the getGrids() function first.");

        // If the coordinates do not fall into the bounds of the grid array
        if(coordinates.getLon() > extremeEast.getLon())
            throw new GridNotContainsPointException("Coordinates " + coordinates + " are too far east.");
        if(coordinates.getLon() < extremeWest.getLon())
            throw new GridNotContainsPointException("Coordinates " + coordinates + " are too far west.");
        if(coordinates.getLat() > extremeNorth.getLat())
            throw new GridNotContainsPointException("Coordinates " + coordinates + " are too far north.");
        if(coordinates.getLat() < extremeSouth.getLat())
            throw new GridNotContainsPointException("Coordinates " + coordinates + " are too far south.");

        //double y = ((extremeNorth.getLat() - coordinates.getLat()) / blockHeight) - 1;
        //double x = -((extremeWest.getLon() - coordinates.getLon()) / blockWidth) - 1;

        double differencey = coordinates.getLat() - extremeNorth.getLat();
        differencey = Math.abs(differencey);
        double y_index = Math.floor(differencey / blockHeight);
        int y = (int) y_index;

        double differencex = coordinates.getLon() - extremeWest.getLon();
        differencex = Math.abs(differencex);
        double x_index = Math.floor(differencex / blockWidth);
        int x = (int) x_index;

        return new int[]{y,x};

        /*
        if( (int) Math.abs(Math.abs(y) - Math.round(Math.abs(y))) >= 0.01 || Math.abs(Math.abs(x) - Math.round(Math.abs(x))) >= 0.01) {
            System.out.println("RETURNED DOUBLES NOT INTS");

            System.out.println( (int) Math.abs(Math.abs(y) - Math.round(Math.abs(y))) >= 0.01);
            System.out.println(Math.abs(Math.abs(x) - Math.round(Math.abs(x))));

            System.exit(0);
        }

        return new int[]{(int) Math.round(y),(int) Math.round(x)};
        */
    }

    /**
     * Calculate the crime concentration (Ex. 10 crimes/grid) percentiles for the dataset
     *
     * @param grids The grids in the dataset
     * @param percentiles The percentiles to calculate (Ex. [10,20,30] --> 10th, 20th, and 30th percentiles calculated)
     * @return Percentile objects (10th percentile == 1000 crimes pair)
     */
    public Percentile[] generatePercentiles(Grid[][] grids, int[] percentiles) {

        // Keep track of the number of reported incidents in each grid
        // This allows us to calculate percentiles in the dataset
        //   * Ascending order
        //   * Excluding zero-size grids
        ArrayList<Integer> reportedIncidentsCount = new ArrayList<Integer>();

        // Calculate the number of incidents in each grid (excluding zero-grids)
        System.out.println();
        for(int y = 0; y < grids.length; y++) {
            for(int x = 0; x < grids[y].length; x++) {
                if(grids[y][x].numberOfIncidents() == 0)
                    continue;
                reportedIncidentsCount.add(grids[y][x].numberOfIncidents());
                System.out.print(grids[y][x].numberOfIncidents() + ",");
            }
        }


        Collections.sort(reportedIncidentsCount);


        // Store each requested percentile offset in an array
        Percentile[] percentileObjects = new Percentile[percentiles.length];

        for(int p = 0; p < percentiles.length; p++) {
            double percentile = percentiles[p] / 100.0;

            double indx = percentile * reportedIncidentsCount.size();
            int index = 0;

            if(PDDMath.getInstance().isWholeNumber(indx)) {
                index = (int) indx;
            } else {
                index = (int) Math.ceil(indx);
            }

            percentileObjects[p] = new Percentile(percentiles[p], reportedIncidentsCount.get(index));
        }

        return percentileObjects;

    }

    /**
     * Get the crime concentration (Ex. 10 crimes/grid) percentiles for the dataset
     * @return Percentile objects (10th percentile == 1000 crimes pair)
     */
    public Percentile[] getPercentiles() {
        return percentiles;
    }

    /**
     * Get the crime concentration (Ex. 10 crimes/grid) percentiles for the dataset AS A JSON ARRAY
     * @return Percentile objects (10th percentile == 1000 crimes pair)
     */
    public JsonArray getPercentilesJson() {
        Percentile[] percentiles = getPercentiles();
        JsonArray percentilesJson = new JsonArray();
        for(int i = 0; i < percentiles.length; i++) {
            percentilesJson.add(percentiles[i].toJson());
        }
        return percentilesJson;
    }
}
