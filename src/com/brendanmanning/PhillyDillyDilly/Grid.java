package com.brendanmanning.PhillyDillyDilly;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class Grid {

    private Coordinates topLeft = null;

    private double height = -1;
    private double width = -1;

    private boolean alreadyCalculatedStartDate = false;
    private DateTime startDate = null;

    private List<Incident> incidents = new ArrayList<Incident>();

    public Grid(Coordinates topLeft, double height, double width) {
        this.topLeft = topLeft;
        this.height = height;
        this.width = width;
    }

    public Coordinates getTopLeft() {
        return this.topLeft;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return this.width;
    }

    public void addIncident(Incident incident) {
        this.incidents.add(incident);
    }

    public List<Incident> getIncidents() {
        return this.incidents;
    }

    public int numberOfIncidents() {
        return incidents.size();
    }

    public DateTime startDate() {

        // If we've already cached the result, return immediately
        if(alreadyCalculatedStartDate)
            return startDate;

        // Calculate the date of the first reported incident
        DateTime earliest = null;
        for(int i = 0; i < incidents.size(); i++) {
            Incident incident = incidents.get(i);

            if(earliest == null) {
                earliest = incident.getDate();
                continue;
            }

            if(incident.getDate().getTime() <= earliest.getTime()) {
                earliest = incident.getDate();
            }
        }

        // Save the start date and note that it was already calculated
        // (This helps if the List size is zero and we still get a null result
        // (Thus, we avoid going through the for loop and can cache even null results)
        startDate = earliest;
        alreadyCalculatedStartDate = true;

        return startDate;
    }

    public boolean contains(Incident incident) {
        return contains(incident.coordinates);
    }

    public boolean contains(Coordinates coordinates) {
        double ydiff = coordinates.getLat() - topLeft.getLat();
        boolean yvalid = ydiff <= 0 && Math.abs(ydiff) < height;

        double xdiff = coordinates.getLon() - topLeft.getLon();
        boolean xvalid = xdiff >= 0 && Math.abs(xdiff) < width;

        return yvalid && xvalid;
    }

    public JsonObject toJson() {

        JsonArray incidentsJson = new JsonArray();
        for(int i = 0; i < incidents.size(); i++) {
            incidentsJson.add(incidents.get(i).toJson());
        }

        JsonObject coordinates = new JsonObject();
        coordinates.addProperty("lat", topLeft.getLat());
        coordinates.addProperty("lon", topLeft.getLon());

        JsonObject json = new JsonObject();
        json.add("topLeft", coordinates);
        json.add("incidents", incidentsJson);

        return json;
    }

}
