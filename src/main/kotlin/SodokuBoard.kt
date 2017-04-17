class SudokuBoard(theBoxes: Collection<SudokuBox>) {

    val boxes = HashSet(theBoxes)
    val maxX = boxes.stream().mapToInt { it.maxX() }.max().asInt
    val maxY = boxes.stream().mapToInt { it.maxY() }.max().asInt

    fun includes(cell: SudokuCell): Boolean {
        return boxes.any { it.includes(cell) }
    }

    fun canAdd(cell: SudokuCell, value: Int, fixedCells: Map<SudokuCell, Int>): Boolean {
        return boxes.all { it.canAdd(cell, value, fixedCells) }
    }
}
