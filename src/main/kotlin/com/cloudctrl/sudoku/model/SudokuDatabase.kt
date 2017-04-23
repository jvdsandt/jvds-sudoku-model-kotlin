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
            arrayOf(8, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 0, 3, 6, 0, 0, 0, 0, 0),
            arrayOf(0, 7, 0, 0, 9, 0, 2, 0, 0),
            arrayOf(0, 5, 0, 0, 0, 7, 0, 0, 0),
            arrayOf(0, 0, 0, 0, 4, 5, 7, 0, 0),
            arrayOf(0, 0, 0, 1, 0, 0, 0, 3, 0),
            arrayOf(0, 0, 1, 0, 0, 0, 0, 6, 8),
            arrayOf(0, 0, 8, 5, 0, 0, 0, 1, 0),
            arrayOf(0, 9, 0, 0, 0, 0, 4, 0, 0)))
    return builder.newGame()
}

fun sampleHardGameSolution(): Array<Array<Int>> {
    return arrayOf(
            arrayOf(8, 1, 2, 7, 5, 3, 6, 4, 9),
            arrayOf(9, 4, 3, 6, 8, 2, 1, 7, 5),
            arrayOf(6, 7, 5, 4, 9, 1, 2, 8, 3),
            arrayOf(1, 5, 4, 2, 3, 7, 8, 9, 6),
            arrayOf(3, 6, 9, 8, 4, 5, 7, 2, 1),
            arrayOf(2, 8, 7, 1, 6, 9, 5, 3, 4),
            arrayOf(5, 2, 1, 9, 7, 4, 3, 6, 8),
            arrayOf(4, 3, 8, 5, 2, 6, 9, 1, 7),
            arrayOf(7, 9, 6, 3, 1, 8, 4, 5, 2))

}