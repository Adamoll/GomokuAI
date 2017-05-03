package SI3;

import SI3.Heuristics.*;

import java.util.ArrayList;
import java.util.List;

public class MiniMax {

    private int[][] board;
    GameLogic gameLogic;
    HeuristicInterface heuristic;

    MiniMax(int[][] board, GameLogic gameLogic) {
        this.board = board;
        this.gameLogic = gameLogic;
        heuristic = new BlockingHeuristic(board);
    }


    public int[] miniMax(int depth, boolean isPlayer) {
        List<int[]> moves = getMoves();
        int bestRow = -1;
        int bestCol = -1;
        int bestScore = isPlayer ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        int currentScore;
        int player = isPlayer ? 1 : 2;
        if (moves.isEmpty() || depth == 0) {
            bestScore = heuristic.evaluate(player);
            return new int[]{bestScore, bestRow, bestCol};
        }
        for (int[] move : moves) {
            board[move[0]][move[1]] = player;
            if (!isPlayer) {
                currentScore = miniMax(depth - 1, true)[0];
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    bestRow = move[0];
                    bestCol = move[1];
                }
            } else if (isPlayer) {
                currentScore = miniMax(depth - 1, false)[0];
                if (currentScore < bestScore) {
                    bestScore = currentScore;
                    bestRow = move[0];
                    bestCol = move[1];
                }

            }
            board[move[0]][move[1]] = GameLogic.EMPTY;
        }

        return new int[]{bestScore, bestRow, bestCol};
    }

    private List<int[]> getMoves() {
        ArrayList<int[]> moves = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == GameLogic.EMPTY) {
                    moves.add(new int[]{row, col});
                }
            }
        }
        return moves;
    }

    protected void setHeuristic(String heuristic) {
        if(heuristic.equals("Line")) {
            this.heuristic = new LineHeuristic(board);
        } else if (heuristic.equals("Blocking")) {
            this.heuristic = new BlockingHeuristic(board);
        }
    }


}
