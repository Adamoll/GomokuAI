package Gomoku;

import Gomoku.Heuristics.*;

import java.util.ArrayList;
import java.util.List;

public class MiniMax implements SearchAlgoritm {
    protected int[][] board;
    protected GameLogic gameLogic;
    protected HeuristicInterface heuristicBlacks;
    protected HeuristicInterface heuristicWhites;
    protected long startTime, stopTime;

    MiniMax(int[][] board, GameLogic gameLogic) {
        this.board = board;
        this.gameLogic = gameLogic;
        heuristicWhites = new LineHeuristic(board);
        heuristicBlacks = new LineHeuristic(board);
    }

    public int[] miniMax(int depth, boolean isOpponent, boolean isBlack) {
        List<int[]> moves = getMoves();
        int bestRow = -1;
        int bestCol = -1;
        int bestScore = isOpponent ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        int currentScore = 0;
        int player = isBlack ? GameLogic.BLACK : GameLogic.WHITE;

        if (moves.isEmpty() || depth == 0) {
            if (gameLogic.getNextPlayer() == GameLogic.BLACK)
                bestScore = heuristicBlacks.evaluate(player);
            else if (gameLogic.getNextPlayer() == GameLogic.WHITE)
                bestScore = heuristicWhites.evaluate(player);
            return new int[]{bestScore, bestRow, bestCol};
        }
        for (int[] move : moves) {
            board[move[0]][move[1]] = player;
            if (!isOpponent) {
                currentScore = miniMax(depth - 1, !isOpponent, !isBlack)[0];
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    bestRow = move[0];
                    bestCol = move[1];
                }
            } else if (isOpponent) {
                currentScore = miniMax(depth - 1, !isOpponent, !isBlack)[0];
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

    public int[] run(int depth, boolean isOpponent) {
        int[][] clone = gameLogic.cloneBoard();
        board = clone;
        heuristicWhites.setBoard(clone);
        heuristicBlacks.setBoard(clone);
        boolean isBlack = gameLogic.getNextPlayer() == GameLogic.BLACK;
        startTime = System.currentTimeMillis();
        int[] result = miniMax(depth, isOpponent, isBlack);
        stopTime = System.currentTimeMillis();
        System.out.println((stopTime - startTime )/ 1000.0 + " s");
        return result;
    }

    protected List<int[]> getMoves() {
        ArrayList<int[]> moves = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == GameLogic.EMPTY && isRationalMove(row, col))
                    moves.add(new int[]{row, col});
            }
        }
        return moves;
    }

    private boolean isRationalMove(int row, int col) {
        boolean rational = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int x = row + i;
                int y = col + j;
                if (x >= 0 && x < GameLogic.ROWS && y >= 0 && y < GameLogic.COLS) {
                    if (board[x][y] != GameLogic.EMPTY) {
                        rational = true;
                    }
                }
            }
        }
        return rational;
    }

    public void setHeuristic(String heuristic, boolean blacks) {
        if (blacks) {
            if (heuristic.equals("Line")) {
                this.heuristicBlacks = new LineHeuristic(board);
            } else if (heuristic.equals("Blocking")) {
                this.heuristicBlacks = new BlockingHeuristic(board);
            } else if (heuristic.equals("Spatial"))
                this.heuristicBlacks = new SpatialHeuristic(board);
        } else {
            if (heuristic.equals("Line")) {
                this.heuristicWhites = new LineHeuristic(board);
            } else if (heuristic.equals("Blocking")) {
                this.heuristicWhites = new BlockingHeuristic(board);
            } else if (heuristic.equals("Spatial"))
                this.heuristicWhites = new SpatialHeuristic(board);
        }
    }

    @Override
    public HeuristicInterface[] getHeuristic() {
        return new HeuristicInterface[]{heuristicBlacks, heuristicWhites};
    }
}
