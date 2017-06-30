package com.cloudctrl.sudoku.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class OpenSudokuGamesTest {

    @Test fun testParsing() {
        val games = OpenSudokuGamesReader().read(javaClass.getResourceAsStream("/easy.opensudoku"))

        assertEquals(100, games.size)

        /* "379000014060010070080009005435007000090040020000800436900700080040080050850000249" */
        val game = games[0]
        assertEquals(3, game[1, 1])
        assertEquals(2, game[7, 9])
        assertEquals(4, game[8, 9])
        assertEquals(9, game[9, 9])

        /* "072000000916080000345020070090504086008201300460807020080050213000070859000000460" */
        val game2 = games[99]
        assertEquals(9, game2[1, 2])
        assertEquals(6, game2[8, 9])
        assertEquals(null, game2[9, 9])
    }

    @Test fun testEasyGames() {
        val games = OpenSudokuGamesReader().read(javaClass.getResourceAsStream("/easy.opensudoku"))
        val solvedGames = games.map { it -> it.asSolvedGame() }
        assertTrue(solvedGames.all { it -> it.isSolved() })
    }

    @Test fun testMediumGames() {
        val games = OpenSudokuGamesReader().read(javaClass.getResourceAsStream("/medium.opensudoku"))
        val solvedGames = games.map { it -> it.asSolvedGame() }
        assertTrue(solvedGames.all { it -> it.isSolved() })
    }

    @Test fun testHardGames() {
        val games = OpenSudokuGamesReader().read(javaClass.getResourceAsStream("/hard.opensudoku"))
        val solvedGames = games.map { it -> it.asSolvedGame() }
        assertTrue(solvedGames.all { it -> it.isSolved() })
    }

    @Test fun testVeryHardGames() {
        val games = OpenSudokuGamesReader().read(javaClass.getResourceAsStream("/very_hard.opensudoku"))
        val solvedGames = games.map { it -> it.asSolvedGame() }
        assertTrue(solvedGames.all { it -> it.isSolved() })
    }
}