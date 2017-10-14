package com.cloudctrl.sudoku.model

/**
 * Immutable class that represents a Sudoku board.
 */
class SudokuBoard(theBoxes: Collection<SudokuBox>) {

    companion object {

        fun default9x9() : SudokuBoard {
            val builder = SudokuBoardBuilder()
            builder.initStandard()
            return builder.newBoard()
        }
    }

    val boxes = HashSet(theBoxes)
    val maxX = boxes.stream().mapToInt { it.maxX() }.max().asInt
    val maxY = boxes.stream().mapToInt { it.maxY() }.max().asInt
    val allValues: Set<Int> = (1..boxes.stream().mapToInt { it.cells.size }.max().asInt).toSet()

    operator fun contains(cell: SudokuCell): Boolean {
        return boxes.any { cell in it }
    }

    fun canAdd(cell: SudokuCell, value: Int, fixedCells: Map<SudokuCell, Int>) : Boolean {
        return cell.x in 1..maxX && cell.y in 1..maxY && boxes.all { it.canAdd(cell, value, fixedCells) }
    }

    fun boxesFor(cell: SudokuCell, action: (SudokuBox) -> Unit) {
        boxes.forEach { eachBox ->
            if (cell in eachBox) {
                action.invoke(eachBox)
            }
        }
    }

    fun relevantCells() : Set<SudokuCell> {
        val visitedCells = mutableSetOf<SudokuCell>()
        boxes.forEach { eachBox -> visitedCells.addAll(eachBox.cells) }
        return visitedCells
    }

    fun relevantCellsDo(action: (SudokuCell) -> Unit) {
        val visitedCells = mutableSetOf<SudokuCell>()
        boxes.forEach { eachBox ->
            eachBox.cells.forEach { eachCell ->
                if (eachCell !in visitedCells) {
                    action.invoke(eachCell)
                    visitedCells.add(eachCell)
                }
            }
        }
    }

    fun cellSharingBoxDo(cell: SudokuCell, action: (SudokuCell) -> Unit) {
        val visitedCells = mutableSetOf<SudokuCell>()
        boxes.forEach { eachBox ->
            if (cell in eachBox) {
                eachBox.cells.forEach { eachCell ->
                    if (eachCell != cell && eachCell !in visitedCells) {
                        action.invoke(eachCell)
                        visitedCells.add(eachCell)
                    }
                }
            }
        }
    }

    fun getPossibleValues(cell : SudokuCell, fixedCells: Map<SudokuCell, Int>) : Set<Int> {
        var values = allValues
        boxesFor(cell, { eachBox ->
            values = eachBox.getPossibleValues(cell, values, fixedCells)
        })
        return values
    }

    fun getPossibleValuesPerCell(fixedCells: Map<SudokuCell, Int>) : Map<SudokuCell, Set<Int>> {
        val cellOptions = mutableMapOf<SudokuCell, Set<Int>>()
        relevantCellsDo { eachCell ->
            if (!fixedCells.containsKey(eachCell)) {
                cellOptions[eachCell] = getPossibleValues(eachCell, fixedCells)
            }
        }
        return cellOptions
    }

    fun processMove(optionsPerCell: Map<SudokuCell, Set<Int>>, move: SudokuMove) : Map<SudokuCell, Set<Int>> {
        val newOptions = optionsPerCell.toMutableMap()
        newOptions.remove(move.cell)
        cellSharingBoxDo(move.cell, { eachCell ->
            val newValues = newOptions[eachCell]
            if (newValues != null) {
                newOptions[eachCell] = newValues.minus(move.value)
            }
        })
        return newOptions
    }
}

/**
 * Helper class to build SudokuBoard instances.
 */
class SudokuBoardBuilder() {

    val boxes = mutableListOf<SudokuBox>()

    fun addBox(name: String, fromCell: SudokuCell, toCell: SudokuCell) {
        boxes.add(SudokuBox(name, fromCell, toCell))
    }

    fun initStandard(maxX: Int = 9, maxY: Int = 9) {
        for (i in 1..maxY) {
            addBox("row-${i}", SudokuCell(1, i), SudokuCell(maxX, i))
        }
        for (i in 1..maxX) {
            addBox("column-${i}", SudokuCell(i, 1), SudokuCell(i, maxY))
        }
        for (ypos in 1..maxY step 3) {
            for (xpos in 1..maxX step 3) {
                addBox("box-${xpos}x${ypos}", SudokuCell(xpos, ypos), SudokuCell(xpos+2, ypos+2))
            }
        }
    }

    fun newBoard() : SudokuBoard {
        return SudokuBoard(boxes)
    }
}
