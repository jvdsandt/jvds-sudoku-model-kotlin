package com.cloudctrl.sudoku.model

/**
 * Immutable class that represents a 'box' on a Sudoku board. Usually this
 * is a square, a row or a column of cells. Multiple cells in the same box
 * cannot have the same value.
 */
class SudokuBox(val name: String, val cells: Set<SudokuCell>) {

    constructor(name: String, minCell: SudokuCell, maxCell: SudokuCell) : this(name, createCells(minCell, maxCell))

    fun maxX(): Int {
        return cells.stream().mapToInt({ it.x }).max().asInt
    }

    fun maxY(): Int {
        return cells.stream().mapToInt({ it.y }).max().asInt
    }

    operator fun contains(cell: SudokuCell): Boolean = cells.contains(cell)

    fun canAdd(cell: SudokuCell, value: Int, fixedCells: Map<SudokuCell, Int>): Boolean {
        if (cell !in this) {
            return true
        }
        return !(cells.any { fixedCells.get(it) == value })
    }

    fun getPossibleValues(cell: SudokuCell, values: Set<Int>, fixedCells: Map<SudokuCell, Int>) : Set<Int> {
        val result = values.toMutableSet()
        cells.forEach { eachCell ->
            if (eachCell != cell) {
                val value = fixedCells[eachCell]
                if (value != null) {
                    result.remove(value)
                }
            }
        }
        return result
    }

    fun findMoveWith(options: Map<SudokuCell, Set<Int>>) : SudokuMove? {
        val cellsPerValue = mutableMapOf<Int, MutableSet<SudokuCell>>()
        cells.forEach { eachCell ->
            (options.getOrDefault(eachCell, emptySet())).forEach { eachValue ->
                cellsPerValue.getOrPut(eachValue, { mutableSetOf() }).add(eachCell)
            }
        }
        for ((eachValue, eachCells) in cellsPerValue) {
            if (eachCells.size == 1) {
                return SudokuMove(eachCells.single(), eachValue, SudokuMoveReason.ONLY_PLACE)
            }
        }
        return null
    }

}

private fun createCells(minCell: SudokuCell, maxCell: SudokuCell): Set<SudokuCell> {
    val cells = HashSet<SudokuCell>()
    for (ypos in minCell.y..maxCell.y) {
        for (xpos in minCell.x..maxCell.x) {
            cells.add(SudokuCell(xpos, ypos))
        }
    }
    return cells
}

