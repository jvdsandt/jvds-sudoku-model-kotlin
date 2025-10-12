package com.cloudctrl.sudoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SimpleSudokuServiceTest {

    private SimpleSudokuService service;

    @BeforeEach
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
