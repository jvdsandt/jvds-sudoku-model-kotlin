package com.cloudctrl.sudoku.model;

public class SimpleSudokuServiceImpl implements SimpleSudokuService {

    public SudokuMove getNextMove(String gameNumberLine) {
        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.initFromNumberLine(gameNumberLine);
        SudokuGameBase game = builder.newGame();
        game = game.doNextMove();
        if (game instanceof SudokuGamePlay) {
            return ((SudokuGamePlay) game).getLastMove();
        }
        return null;
    }

    public String solve(String gameNumberLine) {
        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.initFromNumberLine(gameNumberLine);
        SudokuGameBase game = builder.newGame();
        game = game.asSolvedGame();
        return game.asNumberLine();
    }
}
