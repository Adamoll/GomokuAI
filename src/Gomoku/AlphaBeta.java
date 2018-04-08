package Gomoku;

import java.util.List;

public class AlphaBeta extends MiniMax implements SearchAlgoritm {

    AlphaBeta(int[][] board, GameLogic gameLogic) {
        super(board, gameLogic);
    }

    public int[] miniMax(int depth, boolean isOpponent, boolean isBlack, int alpha, int beta) {
        List<int[]> moves = getMoves();
        int bestRow = -1;
        int bestCol = -1;
        int currentScore = 0;
        int player = isBlack ? GameLogic.BLACK : GameLogic.WHITE;
        if (moves.isEmpty() || depth == 0) {
            if (gameLogic.getNextPlayer() == GameLogic.BLACK)
                currentScore = heuristicBlacks.evaluate(player);
            else if (gameLogic.getNextPlayer() == GameLogic.WHITE)
                currentScore = heuristicWhites.evaluate(player);
            return new int[]{currentScore, bestRow, bestCol};
        }
        for (int[] move : moves) {
            board[move[0]][move[1]] = player;
            if (!isOpponent) {
                currentScore = miniMax(depth - 1, !isOpponent, !isBlack, alpha, beta)[0];
                if (currentScore > alpha) {
                    alpha = currentScore;
                    bestRow = move[0];
                    bestCol = move[1];
                }
            } else if (isOpponent) {
                currentScore = miniMax(depth - 1, !isOpponent, !isBlack, alpha, beta)[0];
                if (currentScore < beta) {
                    beta = currentScore;
                    bestRow = move[0];
                    bestCol = move[1];
                }

            }
            board[move[0]][move[1]] = GameLogic.EMPTY;
            if (alpha >= beta)
                break;
        }

        return new int[]{!isOpponent ? alpha : beta, bestRow, bestCol};
    }

    public int[] run(int depth, boolean isOpponent) {
        int[][] clone = gameLogic.cloneBoard();
        board = clone;
        heuristicWhites.setBoard(clone);
        heuristicBlacks.setBoard(clone);
        boolean isBlack = gameLogic.getNextPlayer() == GameLogic.BLACK;
        startTime = System.currentTimeMillis();
        int[] result = miniMax(depth, isOpponent, isBlack, Integer.MIN_VALUE, Integer.MAX_VALUE);
        stopTime = System.currentTimeMillis();
        System.out.println((stopTime - startTime )/ 1000.0 + " s");
        return result;
    }
}