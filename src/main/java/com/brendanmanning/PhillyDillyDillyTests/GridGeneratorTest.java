package com.brendanmanning.PhillyDillyDillyTests;

import com.brendanmanning.PhillyDillyDilly.Coordinates;
import com.brendanmanning.PhillyDillyDilly.Grid;
import com.brendanmanning.PhillyDillyDilly.Incident;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
class GridGeneratorTest {
    @Test
    void getGrids() {

    }

    @Test
    void getGridIndex() {

        // Test errors
        //Coordinates tooFarLeft = new Coordinates()
    }

    @Test
    void getPercentiles() {

        Grid[] grids = new Grid[7];

        // Create random grids ...
        // the values themselves don't matter, just the number of incidents we report in the next step
        grids[0] = new Grid(new Coordinates(0,0),0,0);
        grids[1] = new Grid(new Coordinates(1,1),1,1);
        grids[2] = new Grid(new Coordinates(2,2),2,2);
        grids[3] = new Grid(new Coordinates(3,3),3,3);
        grids[4] = new Grid(new Coordinates(4,4),4,4);
        grids[5] = new Grid(new Coordinates(5,5),5,5);
        grids[6] = new Grid(new Coordinates(6,6),6,6);

        // Create a random incident
        // ... Again, doesn't matter what it is, just how many times we add it
        Incident incident = new Incident("0101000020E6100000447E479E35C552C076963005B1074440,-75.0813976,121,02,3,2015-06-22 21:13:00,2015-06-22,21:13:00,0101000020110F00004C70BABC21E25FC1C0F4E28C6D985241,201502040130,1100 BLOCK COTTMAN AVE,600,Thefts,-75.0813976,40.06008973,40.06008973,21");

        // Set the number of incidents
        // ... Pattern is
        grids[0].addIncident(new Incident(""));

    }

}