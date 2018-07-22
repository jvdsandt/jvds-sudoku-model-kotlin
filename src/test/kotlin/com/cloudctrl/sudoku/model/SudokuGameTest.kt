package com.cloudctrl.sudoku.model

import org.junit.Test
import kotlin.test.*

class SudokuGameTest {

    @Test fun testBuildGame() {
        val builder = SudokuGameBuilder()
        builder.fix(SudokuCell(1, 1), 4)
        builder.fix(SudokuCell(2, 2), 5)

        val game = builder.newGame()
        assertEquals(
                mapOf(SudokuCell(1, 1) to 4, SudokuCell(2, 2) to 5),
                game.fixedCells)
    }

    @Test fun testBuildFromArray() {
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

        val game = builder.newGame()
        assertEquals(game[1, 1], null)
        assertEquals(game[1, 9], 7)
    }

    @Test fun testBuildInvalid() {
        val builder = SudokuGameBuilder()
        builder.fix(SudokuCell(1, 1), 4)
        assertFailsWith(IllegalArgumentException::class, { builder.fix(SudokuCell(2, 2), 4) })
    }

    @Test fun testBuildFromLine() {
        val builder = SudokuGameBuilder()
        builder.initFromNumberLine("900070003050300800060004109020089001000103000100750060506900040003007010700010006");
        val game = builder.newGame()
        assertEquals(9, game[1,1])
        assertEquals(6, game[9,9])
        assertNull(game[2,1])
    }

    @Test fun testBuildFromInvalidLine() {
        val builder = SudokuGameBuilder()
        assertFailsWith(IllegalArgumentException::class, {
            builder.initFromNumberLine("--invalid--")
        })
    }

    @Test fun testSolveSimpleGame() {
        val game = sampleSimpleGame()
        assertEquals(45, game.numberOfCellsToSolve)

        val play = game.doNextMove()
        assertEquals(44, play.numberOfCellsToSolve)

        val solvedPlay = play.asSolvedGame()
        println("Solved simple game:\n${solvedPlay}")
    }

    @Test fun testSolveHardGame() {
        val game = sampleHardGame()
        assertEquals(60, game.numberOfCellsToSolve)

        val play = game.doNextMove()
        assertEquals(59, play.numberOfCellsToSolve)

        val solvedPlay = play.asSolvedGame()
        assertTrue(sampleHardGameSolution().contentDeepEquals(solvedPlay.asArray()))
        println("Solved hard game:\n${solvedPlay}")
    }
}
