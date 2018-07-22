package com.cloudctrl.sudoku.model

/**
 * Base class for Sudoku games and
 */
abstract class SudokuGameBase(val optionsPerCell: Map<SudokuCell, Set<Int>>) {

    abstract val game: SudokuGame

    open val board: SudokuBoard
        get() = this.game.board

    abstract val solvedCells: Map<SudokuCell, Int>

    abstract val numberOfCellsToSolve: Int

    abstract operator fun get(cell: SudokuCell): Int?

    operator fun get(x: Int, y: Int) = this[SudokuCell(x, y)]

    operator fun get(cells: Collection<SudokuCell>): Collection<Int> {
        return cells.map { this[it] }.filterNotNull()
    }

    fun isOpen(cell: SudokuCell) = get(cell) == null

    fun isSolved() = numberOfCellsToSolve == 0

    /**
     * Search the optionPerCell map for a cell that has only one option.
     * If present answer a move for this cell, otherwise answer null.
     */
    fun firstSingleOptionMove(): SudokuMove? {
        for ((eachCell, cellOptions) in optionsPerCell) {
            if (cellOptions.size == 1) {
                return SudokuMove(eachCell, cellOptions.single(), SudokuMoveReason.ONLY_OPTION)
            }
        }
        return null
    }

    fun doNextMove(): SudokuGameBase {
        val move = firstSingleOptionMove()
        if (move != null) {
            return newMove(move)
        }
        board.boxes.forEach { eachBox ->
            val bmove = eachBox.findMoveWith(optionsPerCell)
            if (bmove != null) {
                return newMove(bmove)
            }
        }
        return newMove(takeGuess(), true)
    }

    abstract fun goBackAndMove(): SudokuGamePlay

    fun asSolvedGame(): SudokuGameBase {
        return if (isSolved()) this else this.doNextMove().asSolvedGame()
    }

    fun asArray(): Array<Array<Int>> {
        val result = Array(board.maxY, { _ -> Array(board.maxX, { _ -> 0 }) })
        for (y in 0..board.maxY - 1) {
            for (x in 0..board.maxX - 1) {
                result[y][x] = get(x + 1, y + 1) ?: 0
            }
        }
        return result
    }

    fun asNumberLine(): String {
        val sb = StringBuilder()
        for (y in 1..board.maxY) {
            for (x in 1..board.maxX) {
                sb.append(get(x, y) ?: 0)
            }
        }
        return sb.toString()
    }

    fun asNumberLines(): String {
        val sb = StringBuilder()
        for (y in 1..board.maxY) {
            for (x in 1..board.maxX) {
                sb.append(' ')
                sb.append(get(x, y) ?: 0)
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    override fun toString(): String {
        return asNumberLines()
    }

    private fun newMove(move: SudokuMove, guessed: Boolean = false): SudokuGameBase {
        val newOptions = board.processMove(optionsPerCell, move)
        if (newOptions.any { (_, values) -> values.isEmpty() }) {
            return goBackAndMove()
        }
        return SudokuAutoGame(this, move, guessed, newOptions)
    }

    private fun takeGuess(): SudokuMove {
        var cell: SudokuCell? = null
        var values: Set<Int>? = null
        for ((eachCell, eachValues) in optionsPerCell) {
            if (values == null || values.size > eachValues.size) {
                cell = eachCell
                values = eachValues
            }
        }
        if (cell != null && values != null) {
            return SudokuMove(cell, values.first(), SudokuMoveReason.GUESS)
        } else {
            throw IllegalStateException()
        }
    }
}

class SudokuGame(override val board: SudokuBoard, val fixedCells: Map<SudokuCell, Int>) :
        SudokuGameBase(board.getPossibleValuesPerCell(fixedCells)) {

    override val game = this

    override val solvedCells = emptyMap<SudokuCell, Int>()

    override val numberOfCellsToSolve: Int = board.relevantCells().size - fixedCells.size

    override operator fun get(cell: SudokuCell) = fixedCells[cell]

    override fun goBackAndMove(): SudokuGamePlay {
        throw IllegalStateException()
    }
}

/**
 * Helper class to create SudokuGame instances.
 */
class SudokuGameBuilder(val board: SudokuBoard) {

    var fixedCells = mutableMapOf<SudokuCell, Int>()

    constructor() : this(SudokuBoard.default9x9())

    companion object {

        fun newGameFromNumberLine(numberLine: String): SudokuGame {
            val board = SudokuBoard.default9x9()
            val builder = SudokuGameBuilder(board)
            builder.initFromNumberLine(numberLine)
            return builder.newGame()
        }
    }


    fun canAdd(cell: SudokuCell, value: Int) = board.canAdd(cell, value, fixedCells)

    fun fix(cell: SudokuCell, value: Int) {
        if (canAdd(cell, value)) {
            fixedCells[cell] = value
        } else {
            throw IllegalArgumentException("Cannot add this cell")
        }
    }

    fun fix(x: Int, y: Int, value: Int) = fix(SudokuCell(x, y), value)

    fun fix(values: Array<Array<Int>>) {
        values.forEachIndexed { rowNr, eachRow ->
            eachRow.forEachIndexed { colNr, eachValue ->
                if (eachValue > 0) {
                    fix(SudokuCell(colNr + 1, rowNr + 1), eachValue)
                }
            }
        }
    }

    fun initFromNumberLine(numberLine: String) {
        if (numberLine.length < board.maxX * board.maxY) {
            throw IllegalArgumentException("Not enough numbers provided")
        }
        for (y in 0..board.maxY - 1) {
            for (x in 0..board.maxX - 1) {
                val value = numberLine[y * board.maxX + x]
                if (value < '0' || value > '9') {
                    throw IllegalArgumentException("Invalid cell value at $x@$y")
                }
                if (value != '0') {
                    fix(x + 1, y + 1, Character.digit(value, 10))
                }
            }
        }
    }

    fun newGame(): SudokuGame {
        return SudokuGame(board, fixedCells.toMap())
    }
}

abstract class SudokuGamePlay(val previousPlay: SudokuGameBase,
                              val lastMove: SudokuMove,
                              val guessed: Boolean,
                              optionsPerCell: Map<SudokuCell, Set<Int>>) :
        SudokuGameBase(optionsPerCell) {

    override val game = previousPlay.game

    override val solvedCells: Map<SudokuCell, Int> by lazy { previousPlay.solvedCells.plus(lastMove.cell to lastMove.value) }

    override val numberOfCellsToSolve: Int by lazy { game.numberOfCellsToSolve - solvedCells.size }

    override operator fun get(cell: SudokuCell): Int? {
        return solvedCells[cell] ?: game[cell]
    }
}

class SudokuAutoGame(previousPlay: SudokuGameBase, lastMove: SudokuMove, guessed: Boolean, optionsPerCell: Map<SudokuCell, Set<Int>>) :
        SudokuGamePlay(previousPlay, lastMove, guessed, optionsPerCell) {

    override fun goBackAndMove(): SudokuGamePlay {
        if (!guessed) {
            return previousPlay.goBackAndMove()
        }
        val newOptions = previousPlay.optionsPerCell.toMutableMap()
        newOptions[lastMove.cell] = newOptions[lastMove.cell]!!.minus(lastMove.value)
        return SudokuAutoGame(previousPlay, lastMove, false, newOptions)
    }
}
