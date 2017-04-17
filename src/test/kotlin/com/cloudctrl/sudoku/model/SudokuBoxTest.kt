package com.cloudctrl.sudoku.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SudokuBoxTest {

    @Test fun testMaxX() {
        val box = SudokuBox("test",
                SudokuCell(1, 1),
                SudokuCell(2, 4))
        assertEquals(2, box.maxX())
        assertEquals(4, box.maxY())
    }

    @Test fun testCanAdd() {
        val box = SudokuBox("test",
                SudokuCell(1, 1),
                SudokuCell(3, 3))
        val fixedCells = hashMapOf(SudokuCell(2, 2) to 9)

        assertTrue { box.canAdd(SudokuCell(1, 1), 8, fixedCells) }
        assertFalse { box.canAdd(SudokuCell(1, 1), 9, fixedCells) }
        assertTrue { box.canAdd(SudokuCell(4, 4), 9, fixedCells) }
    }
}