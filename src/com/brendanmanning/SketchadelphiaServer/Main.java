package com.brendanmanning.SketchadelphiaServer;

/**
 * SketchadelphiaServer
 * Copyright 2018 Brendan Manning. All rights reserved.
 */

import com.brendanmanning.PhillyDillyDilly.Grid;
import com.brendanmanning.PhillyDillyDilly.GridGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import spark.Response;

import java.io.IOException;
import java.net.MalformedURLException;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        // Configure Spark
        port(80);
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        com.brendanmanning.PhillyDillyDilly.Main.main(new String[]{});
        System.out.println("Starting server...");

        get("/directions", (request, response) -> {

            String from = request.queryParams("from");
            String to = request.queryParams("to");
            Route route = null;
            ResponseJSON responseJSON = new ResponseJSON();


            // Make sure all URL parameters filled
            if (from == null || to == null) {
                return ResponseMessages.URL_PARAMETER_MISSING;
            }

            // Request directions from google
            try {

                route = GoogleMaps.getInstance().getRoute(from, to);

                responseJSON.setSuccess(true);
                responseJSON.setMessage("Sketchadelphia request successful.");
                responseJSON.setBlockHeight(GridGenerator.getInstance().getBlockHeight());
                responseJSON.setBlockWidth(GridGenerator.getInstance().getBlockWidth());
                responseJSON.setAverageIncidentsPerGrid(GridGenerator.getInstance().getAverageIncidentsPerGrid());
                responseJSON.setGRIDS_NS(GridGenerator.getInstance().getGRIDS_NS());
                responseJSON.setGRIDS_EW(GridGenerator.getInstance().getGRIDS_EW());
                responseJSON.setGoogleResponse(GoogleMaps.getInstance().getLastResponse());
                responseJSON.setReportedIncidents(route.numberOfIncidents());

            } catch (MalformedURLException mue) {
                responseJSON.setSuccess(false);
                responseJSON.setMessage(mue.getLocalizedMessage());
            } catch (IOException ioe) {
                responseJSON.setSuccess(false);
                responseJSON.setMessage(ioe.getLocalizedMessage());
            }

            // Fill the response
            return responseJSON;
        });


        // Enable CORS from the client website
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        internalServerError((req, res) -> {
            res.type("application/json");

            ResponseJSON responseJSON = new ResponseJSON();
            responseJSON.setSuccess(false);
            responseJSON.setMessage("Invalid starting or ending location.");

            return responseJSON;
        });

    }
}