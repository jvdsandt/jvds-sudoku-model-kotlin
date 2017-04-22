package com.cloudctrl.sudoku.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SudokuBoardTest {

    @Test fun testDefault() {
        val board = SudokuBoard.default9x9()

        assertEquals(9, board.maxX)
        assertEquals(9, board.maxY)
        assertEquals(27, board.boxes.size)
        assertEquals((1..9).toSet(), board.allValues)
    }

    @Test fun testIn() {
        val board = SudokuBoard.default9x9()
        assertTrue { SudokuCell(5, 5) in board }
        assertTrue { SudokuCell(0, 0) !in board }
    }
}