package com.cloudctrl.sudoku.model

import org.junit.Test
import kotlin.test.assertEquals

class SudokuBoardTest {

    @Test fun testDefault() {
        val board = SudokuBoard.default9x9()

        assertEquals(9, board.maxX)
        assertEquals(9, board.maxY)
        assertEquals(27, board.boxes.size)
    }
}