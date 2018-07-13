package com.cloudctrl.sudoku.model

/**
 * Data class to represent a cell on a Sudoku board.
 **/
data class SudokuCell(val x: Int, val y: Int) : Comparable<SudokuCell> {

    override fun compareTo(other: SudokuCell) : Int {
        return if (y == other.y) x - other.x else y - other.y
    }
}

enum class SudokuMoveReason {
    ONLY_OPTION, ONLY_PLACE, GUESS, UNKNOWN
}

/**
 * Data class to represent a cell with a value on a Sudoku board.
 */
data class SudokuMove(val cell: SudokuCell, val value: Int, val reason: SudokuMoveReason = SudokuMoveReason.UNKNOWN)
