package com.brendanmanning.PhillyDillyDillyTests;

import com.brendanmanning.PhillyDillyDillyHelpers.PDDMath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
class PDDMathTest {
    @Test
    void isWholeNumber() {

        assertTrue(PDDMath.getInstance().isWholeNumber(5.0000));
        assertTrue(PDDMath.getInstance().isWholeNumber(0));
        assertTrue(PDDMath.getInstance().isWholeNumber(-1.00000));
        assertTrue(PDDMath.getInstance().isWholeNumber(5));
        assertTrue(PDDMath.getInstance().isWholeNumber(1));

        assertFalse(PDDMath.getInstance().isWholeNumber(3.14));
        assertFalse(PDDMath.getInstance().isWholeNumber(-3.14));
        assertFalse(PDDMath.getInstance().isWholeNumber(0.000004));
        assertFalse(PDDMath.getInstance().isWholeNumber(-0.000004));
        assertFalse(PDDMath.getInstance().isWholeNumber(0.99999999));
        assertFalse(PDDMath.getInstance().isWholeNumber(-0.99999999));

    }

}