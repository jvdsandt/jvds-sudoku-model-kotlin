package com.cloudctrl.sudoku.model

abstract class SudokuGameBase {

    abstract operator fun get(cell: SudokuCell) : Int?

    operator fun get(cells : Collection<SudokuCell>) : Collection<Int> {
        return cells.map { this[it] }.filterNotNull()
    }

    fun isOpen(cell: SudokuCell) = get(cell) == null
}

class SudokuGame(val board: SudokuBoard, val fixedCells: Map<SudokuCell, Int>) : SudokuGameBase() {

    override operator fun get(cell: SudokuCell) = fixedCells.get(cell)

    fun findOpenCellValues(): Map<SudokuCell, Set<Int>> {
        val cellOptions = mutableMapOf<SudokuCell, Set<Int>>()
        board.boxes.forEach { eachBox ->
            val openValues = board.allValues.subtract(this[eachBox.cells])
            eachBox.cells.forEach{ eachCell ->
                if (isOpen(eachCell)) {
                    val possibleValues = cellOptions.getOrDefault(eachCell, board.allValues).subtract(openValues)
                    if (possibleValues.isEmpty()) {
                       throw RuntimeException("unsolvable")
                    }
                    cellOptions.put(eachCell, possibleValues)
                }
            }
        }
        return cellOptions
    }

}
