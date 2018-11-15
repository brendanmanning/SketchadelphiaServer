package com.brendanmanning.SketchadelphiaServer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * SketchadelphiaServer
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

public class ResponseJSON {

    private boolean success = false;
    private String message = "";

    private JsonObject googleResponse = null;

    private int GRIDS_EW = 0;
    private int GRIDS_NS = 0;

    private double blockWidth = 0;
    private double blockHeight = 0;

    private int averageIncidentsPerGrid = 0;
    private int maxIncidentsInAGrid = 0;

    private int reportedIncidents = 0;
    private int startDate = 0;


    public ResponseJSON() {
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public JsonObject getGoogleResponse() {
        return this.googleResponse;
    }

    public int getGRIDS_EW() {
        return this.GRIDS_EW;
    }

    public int getGRIDS_NS() {
        return this.GRIDS_NS;
    }

    public double getBlockHeight() {
        return this.blockHeight;
    }

    public double getBlockWidth() {
        return this.blockWidth;
    }

    public int getAverageIncidentsPerGrid() { return this.averageIncidentsPerGrid; }

    public int getMaxIncidentsInAGrid() { return this.maxIncidentsInAGrid; }

    public int getReportedIncidents() {
        return this.reportedIncidents;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setGoogleResponse(JsonObject googleResponse) {
        this.googleResponse = googleResponse;
    }

    public void setGRIDS_EW(int GRIDS_EW) {
        this.GRIDS_EW = GRIDS_EW;
    }

    public void setGRIDS_NS(int GRIDS_NS) {
        this.GRIDS_NS = GRIDS_NS;
    }

    public void setBlockHeight(double blockHeight) {
        this.blockHeight = blockHeight;
    }

    public void setBlockWidth(double blockWidth) {
        this.blockWidth = blockWidth;
    }

    public void setAverageIncidentsPerGrid(int avg) { this.averageIncidentsPerGrid = avg; }

    public void setMaxIncidentsInAGrid(int max) { this.maxIncidentsInAGrid = max; }

    public void setReportedIncidents(int reportedIncidents) {
        this.reportedIncidents = reportedIncidents;
    }

    public String toString() {
        JsonObject json = new JsonObject();
        json.addProperty("success", success);
        json.addProperty("message", message);
        json.addProperty("GRIDS_NS", GRIDS_NS);
        json.addProperty("GRIDS_EW", GRIDS_EW);
        json.addProperty("blockHeight", blockHeight);
        json.addProperty("blockWidth", blockWidth);
        json.addProperty("averageIncidentsPerGrid", averageIncidentsPerGrid);
        json.addProperty("maxIncidentsInAGrid", maxIncidentsInAGrid);
        json.addProperty("reportedIncidents", reportedIncidents);
        json.add("googleResponse", new Gson().fromJson(googleResponse.toString(), JsonElement.class));
        return json.toString();
    }

}
