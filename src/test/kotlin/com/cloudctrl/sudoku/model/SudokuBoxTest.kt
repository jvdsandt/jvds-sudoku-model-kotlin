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

    @Test fun testIn() {
        val box = SudokuBox("test",
                SudokuCell(1, 1),
                SudokuCell(3, 3))
        assertTrue { SudokuCell(2, 2) in box }
        assertFalse { SudokuCell(4, 4) in box }
    }

    @Test fun testPossibleValues() {
        val box = SudokuBox("test",
                SudokuCell(1, 1),
                SudokuCell(3, 3))
        val allValues = (1..9).toSet()

        assertEquals(
                (1..9).toSet(),
                box.getPossibleValues(SudokuCell(3, 3), allValues, emptyMap()))

        val fixedValues = mapOf(SudokuCell(1,1) to 8)
        assertEquals(
                (1..9).toSet().minus(8),
                box.getPossibleValues(SudokuCell(3, 3), allValues, fixedValues))

        val fixedValues2 = mapOf(SudokuCell(1, 1) to 4, SudokuCell(3, 3) to 5)
        assertEquals(
                (1..9).toSet().minus(4).minus(5),
                box.getPossibleValues(SudokuCell(2, 2), allValues, fixedValues2))
    }
}