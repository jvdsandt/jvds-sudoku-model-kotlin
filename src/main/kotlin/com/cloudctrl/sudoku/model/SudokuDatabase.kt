package com.cloudctrl.sudoku.model

fun sampleSimpleGame(): SudokuGame {
    val builder = SudokuGameBuilder()
    builder.fix(arrayOf(
            arrayOf(0, 0, 0, 2, 6, 0, 7, 0, 1),
            arrayOf(6, 8, 0, 0, 7, 0, 0, 9, 0),
            arrayOf(1, 9, 0, 0, 0, 4, 5, 0, 0),
            arrayOf(8, 2, 0, 1, 0, 0, 0, 4, 0),
            arrayOf(0, 0, 4, 6, 0, 2, 9, 0, 0),
            arrayOf(0, 5, 0, 0, 0, 3, 0, 2, 8),
            arrayOf(0, 0, 9, 3, 0, 0, 0, 7, 4),
            arrayOf(0, 4, 0, 0, 5, 0, 0, 3, 6),
            arrayOf(7, 0, 3, 0, 1, 8, 0, 0, 0)))

    return builder.newGame()
}

fun sampleHardGame(): SudokuGame {
    val builder = SudokuGameBuilder()
    builder.fix(arrayOf(
            arrayOf(1, 0, 0, 0, 0, 7, 0, 9, 0),
            arrayOf(0, 3, 0, 0, 2, 0, 0, 0, 8),
            arrayOf(0, 0, 9, 6, 0, 0, 5, 0, 0),
            arrayOf(0, 0, 5, 3, 0, 0, 9, 0, 0),
            arrayOf(0, 1, 0, 0, 8, 0, 0, 0, 2),
            arrayOf(6, 0, 0, 0, 0, 4, 0, 0, 0),
            arrayOf(3, 0, 0, 0, 0, 0, 0, 1, 0),
            arrayOf(0, 4, 0, 0, 0, 0, 0, 0, 7),
            arrayOf(0, 0, 7, 0, 0, 0, 3, 0, 0)))
    return builder.newGame()
}