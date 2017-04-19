package com.cloudctrl.sudoku.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class SudokuDataClassesTest {

    @Test fun testCell() {
        val cell = SudokuCell(1, 2)
        assertEquals(SudokuCell(1, 2), cell)
        assertEquals(SudokuCell(1, 2).hashCode(), cell.hashCode())
        assertNotEquals(SudokuCell(2, 3), cell)
    }

    @Test fun testDestructing() {
        val (x, y) = SudokuCell(4, 5)
        assertEquals(4, x)
        assertEquals(5, y)
    }

    @Test fun testCopy() {
        val cell = SudokuCell(1, 2)
        val copy1 = cell.copy()
        assertEquals(cell, copy1)
        val copy2 = cell.copy(x = 99)
        assertNotEquals(cell, copy2)
        assertEquals(99, copy2.x)
        assertEquals(2, copy2.y)
    }

    @Test fun testCompare() {
        assertTrue { SudokuCell(1,1) < SudokuCell(2, 2) }
        assertTrue { SudokuCell(1,1) < SudokuCell(2, 1) }
        assertTrue { SudokuCell(1, 2) > SudokuCell(99, 1) }
    }
}