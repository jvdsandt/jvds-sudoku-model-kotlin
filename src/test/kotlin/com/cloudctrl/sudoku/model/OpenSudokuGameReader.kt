package com.cloudctrl.sudoku.model

import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory


class OpenSudokuGamesReader {

    val xmlDocBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    fun read(stream: InputStream): List<SudokuGame> {

        val doc = xmlDocBuilder.parse(stream)
        doc.documentElement.normalize()
        val result = mutableListOf<SudokuGame>()

        val gameElems = doc.getElementsByTagName("game")
        for (i in 0..gameElems.length - 1) {
            val gameElem = gameElems.item(i) as Element

            result.add(createGame(gameElem.getAttribute("data")))

        }
        return result
    }

    fun createGame(numberLine: String): SudokuGame {
        return SudokuGameBuilder.newGameFromNumberLine(numberLine)
    }
}