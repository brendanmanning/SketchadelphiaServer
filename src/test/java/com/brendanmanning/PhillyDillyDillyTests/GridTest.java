package com.brendanmanning.PhillyDillyDillyTests;

import com.brendanmanning.PhillyDillyDilly.*;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static com.brendanmanning.PhillyDillyDilly.PDDConfig.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
class GridTest {

    @Test
    void numberOfIncidents() {

        Grid grid = new Grid(new Coordinates(40, -75), 1, 1);
        grid.addIncident(new Incident("0101000020E6100000F8EEE23F15CB52C0EE0F10A3D2F64340,-75.17317197,340,01,1,2015-12-17 07:26:00,2015-12-17,07:26:00,0101000020110F0000C5A766CE1BEC5FC141FEBD3CBA855241,201501056051,1800 BLOCK S CHADWICK ST,1400,Vandalism/Criminal Mischief,-75.17317197,39.92830313,39.92830313,7\n"));

        assertEquals(1, grid.numberOfIncidents());
    }

    @Test
    void startDate() {

        Grid grid = new Grid(new Coordinates(40, -75), 1, 1);
        grid.addIncident(new Incident("0101000020E6100000F8EEE23F15CB52C0EE0F10A3D2F64340,-75.17317197,340,01,1,2015-12-17 07:26:00,2015-12-17,07:26:00,0101000020110F0000C5A766CE1BEC5FC141FEBD3CBA855241,201501056051,1800 BLOCK S CHADWICK ST,1400,Vandalism/Criminal Mischief,-75.17317197,39.92830313,39.92830313,7\n"));
        grid.addIncident(new Incident("0101000020E6100000F8EEE23F15CB52C0EE0F10A3D2F64340,-75.17317197,340,01,1,2015-12-17 07:26:00,2015-12-17,07:26:00,0101000020110F0000C5A766CE1BEC5FC141FEBD3CBA855241,201501056051,1800 BLOCK S CHADWICK ST,1400,Vandalism/Criminal Mischief,-75.17317197,39.92830313,39.92830313,7\n"));
        grid.addIncident(new Incident("0101000020E6100000F8EEE23F15CB52C0EE0F10A3D2F64340,-75.17317197,340,01,1,2015-12-17 07:25:00,2015-12-17,07:25:00,0101000020110F0000C5A766CE1BEC5FC141FEBD3CBA855241,201501056051,1800 BLOCK S CHADWICK ST,1400,Vandalism/Criminal Mischief,-75.17317197,39.92830313,39.92830313,7\n"));
        grid.addIncident(new Incident("0101000020E6100000F8EEE23F15CB52C0EE0F10A3D2F64340,-75.17317197,340,01,1,2015-12-17 07:26:00,2015-12-17,07:26:00,0101000020110F0000C5A766CE1BEC5FC141FEBD3CBA855241,201501056051,1800 BLOCK S CHADWICK ST,1400,Vandalism/Criminal Mischief,-75.17317197,39.92830313,39.92830313,7\n"));

        try {
            DateTime dateTime = new DateTime(PDDConfig.getInstance().PhillyCrimeDataDateFormat.parse("2015-12-17 07:25:00"));
            assertEquals(dateTime.getTime(), grid.startDate().getTime());
        } catch (ParseException pe) {
            assertTrue(false);
        }

    }

    @Test
    void contains() {

        Grid grid = new Grid(new Coordinates(40, -75), 1, 1);

        assertTrue(grid.contains(new Coordinates(40, -75)));
        assertTrue(grid.contains(new Coordinates(40, -74.5)));
        assertTrue(grid.contains(new Coordinates(39.5, -75)));
        assertTrue(grid.contains(new Coordinates(39.5, -74.5)));
        assertTrue(grid.contains(new Coordinates(39.5, -74.9999)));

        assertFalse(grid.contains(new Coordinates(39, -74.999)));
        assertFalse(grid.contains(new Coordinates(39.5, -74)));
        assertFalse(grid.contains(new Coordinates(38.9999, -74.999)));
        assertFalse(grid.contains(new Coordinates(40.0001, -74.5)));
        assertFalse(grid.contains(new Coordinates(39.5, -75.001)));

    }

}