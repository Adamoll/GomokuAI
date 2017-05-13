package Gomoku.Heuristics;

import Gomoku.GameLogic;

public class SpatialHeuristic implements HeuristicInterface {
    String name = "Blocking";
    private int[][] board;

    public SpatialHeuristic(int[][] board) {
        this.board = board;
    }

    @Override
    public int evaluate(int player) {


        int score = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int place = board[row][col];
                if (place == player) {
                    score += check(row, col);
                } else if (place == 3 - player)
                    score -= check(row, col);
            }
        }
        if (score == 0) {
            if (player == GameLogic.BLACK)
                return Integer.MIN_VALUE;
            if (player == GameLogic.WHITE)
                return Integer.MAX_VALUE;
        }
        return score;
    }


    private int check(int row, int col) {
        int score = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                score = 0;
                int x = row + i;
                int y = col + j;
                if (x >= 0 && x < GameLogic.ROWS && y >= 0 && y < GameLogic.COLS) {
                    if (board[x][y] != GameLogic.EMPTY) {
                        score++;
                    }
                }
            }
        }
        return score;

    }

    @Override
    public void setBoard(int[][] board) {
        this.board = board;
    }

    @Override
    public String getName() {
        return name;
    }
}
