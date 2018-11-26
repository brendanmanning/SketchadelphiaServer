package com.brendanmanning.PhillyDillyDilly;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class StatisticsGenerator {
    private static StatisticsGenerator ourInstance = new StatisticsGenerator();

    public static StatisticsGenerator getInstance() {
        return ourInstance;
    }

    private StatisticsGenerator() {
    }
}
