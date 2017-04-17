package com.cloudctrl.sudoku.model

class SudokuBox(val name: String, val cells: Set<SudokuCell>) {

    constructor(name: String, minCell: SudokuCell, maxCell: SudokuCell) : this(name, createCells(minCell, maxCell))

    fun maxX(): Int {
        return cells.stream().mapToInt({ it.x }).max().asInt
    }

    fun maxY(): Int {
        return cells.stream().mapToInt({ it.y }).max().asInt
    }

    fun includes(cell: SudokuCell): Boolean {
        return cells.contains(cell)
    }

    fun canAdd(cell: SudokuCell, value: Int, fixedCells: Map<SudokuCell, Int>): Boolean {
        if (!includes(cell)) {
            return true
        }
        return !(cells.any { fixedCells.get(it) == value })
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

