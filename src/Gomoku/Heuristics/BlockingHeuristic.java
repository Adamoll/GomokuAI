package Gomoku.Heuristics;

import Gomoku.GameLogic;

/**
 * Created by Jan Matejko on 02.05.2017.
 */
public class BlockingHeuristic implements HeuristicInterface {
    String name = "Blocking";
    private int[][] board;

    public BlockingHeuristic(int[][] board) {
        this.board = board;
    }

    public int evaluate(int player) {
        int score = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int place = board[row][col];
                if (place == player) {
                    if (row < board.length - 4) // look upp
                        score += check(row, 1, col, 0, 5);
                    if (col < board[row].length - 4) // look right
                        score += check(row, 0, col, 1, 5);
                    if (col < board[row].length - 4 && row < board.length - 4) // look diagonal right
                        score += check(row, 1, col, 1, 5);
                    if (col > 3 && row < board.length - 4) // look dialogal left
                        score += check(row, 1, col, -1, 5);
                } else if (place == 3 - player) {
                    if (row < board.length - 4) // look upp
                        score -= 2* (check(row, 1, col, 0, 5));
                    if (col < board[row].length - 4) // look right
                        score -= 2* (check(row, 0, col, 1, 5));
                    if (col < board[row].length - 4 && row < board.length - 4) // look diagonal right
                        score -= 2*(check(row, 1, col, 1, 5));
                    if (col > 3 && row < board.length - 4) // look dialogal left
                        score -= 2*(check(row, 1, col, -1, 5));
                }
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

    private int check(int row, int rowDir, int col, int colDir, int neighbours) {
        int player = board[row][col];
        for (int i = 0; i < neighbours; i++) {
            if (board[row + rowDir * i][col + colDir * i] != player) {
                if (i < 2)
                    return 0;
                else
                    return i * i * i * i;
            }
        }
        return neighbours * neighbours * neighbours * neighbours;
    }

    @Override
    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
