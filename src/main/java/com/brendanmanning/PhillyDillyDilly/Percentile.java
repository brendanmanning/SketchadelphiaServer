package com.brendanmanning.PhillyDillyDilly;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class Percentile {

    private int percentile = -1;
    private int lowerBound = -1;

    /**
     * Creates a percentile object with a percentile and bound
     * @param percentile Ex. 10 = 10th percentile
     * @param lowerBound All items where x >= lowerBound are in this percentile. Ex. if 70 is percentile and 50 is lowerBound, all students who scored a 50 or above are in the 70th percentile.
     */
    public Percentile(int percentile, int lowerBound) {
        this.percentile = percentile;
        this.lowerBound = lowerBound;
    }

    /**
     * Get the percentile value contained
     * @return Percentile value
     */
    public int getPercentile() {
        return percentile;
    }

    /**
     * Get the lower bound value
     * @return Lower bound value
     */
    public int getLowerBound() {
        return lowerBound;
    }
}
