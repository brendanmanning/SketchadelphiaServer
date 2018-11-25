package com.brendanmanning.PhillyDillyDillyTests;

import com.brendanmanning.SketchadelphiaServer.GoogleMaps;
import com.brendanmanning.SketchadelphiaServer.Route;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
class GoogleMapsTest {

    @org.junit.jupiter.api.Test
    void constructor() {

        try {

            com.brendanmanning.PhillyDillyDilly.Main.main(new String[]{});

            Route route1 = GoogleMaps.getInstance().getRoute("Drexel University", "Temple University");
            Route route2 = GoogleMaps.getInstance().getRoute("Citizens Bank Park", "Penns Landing");
            Route route3 = GoogleMaps.getInstance().getRoute("Independence Hall", "Northeast Philadelphia Airport");

            assertFalse(route1.hasDuplicatesInHashSet());
            assertFalse(route2.hasDuplicatesInHashSet());
            assertFalse(route3.hasDuplicatesInHashSet());


        } catch (IOException ioe) {
            assertTrue(false);
        }
    }
}