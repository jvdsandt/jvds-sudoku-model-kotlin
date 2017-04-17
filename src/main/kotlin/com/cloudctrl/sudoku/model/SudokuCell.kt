package com.cloudctrl.sudoku.model

data class SudokuCell(val x: Int, val y: Int)

data class SudokuMove(val cell: SudokuCell, val value: Int)
