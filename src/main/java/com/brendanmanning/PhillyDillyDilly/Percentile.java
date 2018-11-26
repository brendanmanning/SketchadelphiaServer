package com.brendanmanning.PhillyDillyDilly;

import com.google.gson.JsonObject;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class Percentile {

    private int percentile = -1;
    private int upperBound = -1;

    /**
     * Creates a percentile object with a percentile and bound
     * @param percentile Ex. 10 = 10th percentile
     * @param upper All items where x >= lowerBound are in this percentile. Ex. if 70 is percentile and 50 is lowerBound, all students who scored a 50 or below are in the 70th percentile.
     */
    public Percentile(int percentile, int upper) {
        this.percentile = percentile;
        this.upperBound = upper;
    }

    /**
     * Get the percentile value contained
     * @return Percentile value
     */
    public int getPercentile() {
        return percentile;
    }

    /**
     * Get the upper bound value
     * @return upper bound value
     */
    public int getUpperBound() {
        return upperBound;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("percentile", percentile);
        jsonObject.addProperty("upperbound", upperBound);
        return jsonObject;
    }
}
