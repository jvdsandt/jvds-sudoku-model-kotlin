package com.cloudctrl.sudoku.model

data class SudokuCell(val xpos: Int, val ypos: Int)

data class SudokuMove(val cell: SudokuCell, val value: Int)
