package Gomoku;

import Gomoku.Heuristics.*;

import java.util.ArrayList;
import java.util.List;

public class MiniMax {

    private int[][] board;
    GameLogic gameLogic;
    HeuristicInterface heuristicBlacks;
    HeuristicInterface heuristicWhites;

    MiniMax(int[][] board, GameLogic gameLogic) {
        this.board = board;
        this.gameLogic = gameLogic;
        heuristicWhites = new LineHeuristic(board);
        heuristicBlacks = new LineHeuristic(board);
    }


//    public int[] miniMax(int depth, boolean isOpponent) {
//        List<int[]> moves = getMoves();
//        int bestRow = -1;
//        int bestCol = -1;
//        int bestScore = isOpponent ? Integer.MAX_VALUE : Integer.MIN_VALUE;
//        int currentScore;
//        int player = isOpponent ? GameLogic.BLACK : GameLogic.WHITE;
//        if (moves.isEmpty() || depth == 0) {
//            if (gameLogic.getNextPlayer() == GameLogic.BLACK)
//                bestScore = heuristicBlacks.evaluate(player);
//            else if (gameLogic.getNextPlayer() == GameLogic.WHITE)
//                bestScore = heuristicWhites.evaluate(player);
//            return new int[]{bestScore, bestRow, bestCol};
//        }
//        for (int[] move : moves) {
//            board[move[0]][move[1]] = player;
//            if (!isOpponent) {
//                currentScore = miniMax(depth - 1, !isOpponent)[0];
//                if (currentScore > bestScore) {
//                    bestScore = currentScore;
//                    bestRow = move[0];
//                    bestCol = move[1];
//                }
//            } else if (isOpponent) {
//                currentScore = miniMax(depth - 1, !isOpponent)[0];
//                if (currentScore < bestScore) {
//                    bestScore = currentScore;
//                    bestRow = move[0];
//                    bestCol = move[1];
//                }
//
//            }
//            board[move[0]][move[1]] = GameLogic.EMPTY;
//        }
//
//        return new int[]{bestScore, bestRow, bestCol};
//    }

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
        int[] result = miniMax(depth, isOpponent, isBlack);
        return result;
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

    protected void setHeuristic(String heuristic, boolean blacks) {
        if (blacks) {
            if (heuristic.equals("Line")) {
                this.heuristicBlacks = new LineHeuristic(board);
            } else if (heuristic.equals("Blocking")) {
                this.heuristicBlacks = new BlockingHeuristic(board);
            }
        } else {
            if (heuristic.equals("Line")) {
                this.heuristicWhites = new LineHeuristic(board);
            } else if (heuristic.equals("Blocking")) {
                this.heuristicWhites = new BlockingHeuristic(board);
            }
        }

    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}