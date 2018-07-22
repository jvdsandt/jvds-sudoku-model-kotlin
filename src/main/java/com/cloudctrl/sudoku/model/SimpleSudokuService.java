package com.cloudctrl.sudoku.model;

public interface SimpleSudokuService {

    SudokuMove getNextMove(String currentGame);

    String solve(String game);
}
