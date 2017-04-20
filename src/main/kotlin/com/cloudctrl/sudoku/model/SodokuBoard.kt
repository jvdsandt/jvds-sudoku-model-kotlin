package com.cloudctrl.sudoku.model

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

    operator fun contains(cell: SudokuCell): Boolean {
        return boxes.any { cell in it }
    }

    fun canAdd(cell: SudokuCell, value: Int, fixedCells: Map<SudokuCell, Int>): Boolean {
        return boxes.all { it.canAdd(cell, value, fixedCells) }
    }
}

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
