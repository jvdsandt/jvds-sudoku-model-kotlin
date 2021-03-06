package com.cloudctrl.sudoku.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SimpleSudokuServiceTest {

    private SimpleSudokuService service;

    @Before
    public void setUp() {
        service = new SimpleSudokuServiceImpl();
    }

    @Test
    public void testBasicsSolve() {
        assertEquals(
            "543978612768152493219364587135426879496783125872591346654217938921835764387649251",
            service.solve("003078000768152003010000500000000009096703120800000000004000030900835764000640200")
        );
    }

    @Test
    public void testInvalidSolve() {
        try {
            service.solve("xyz");
            fail();
        } catch (IllegalArgumentException ex) {
            assertEquals("Not enough numbers provided", ex.getMessage());
        }
    }
}
