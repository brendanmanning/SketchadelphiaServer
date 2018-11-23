package com.brendanmanning.PhillyDillyDillyHelpers;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class PDDMath {

    private static PDDMath ourInstance = new PDDMath();
    public static PDDMath getInstance() {
        return ourInstance;
    }

    public boolean isWholeNumber(double d) {
        return (d - (int) d) == 0;
    }
}
