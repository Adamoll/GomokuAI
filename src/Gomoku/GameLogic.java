package Gomoku;

import Gomoku.Heuristics.HeuristicInterface;

public class GameLogic {
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    public static final int EMPTY = 0;
    public static final int TIE = -1;
    public static int ROWS;
    public static int COLS;
    private int[][] board;
    private int nextPlayer;
    private int moveNumber;
    private int oldHoveredX;
    private int oldHoveredY;
    private boolean gameOver;

    private String blacksPlayer;
    private String whitesPlayer;

    private BoardPanel boardPanel;
    private MiniMax miniMax;
    private AlphaBeta alphaBeta;
    private SearchAlgoritm algoritm;
    private Thread AIGame;

    GameLogic(int ROWS, int COLS, BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.board = new int[ROWS][COLS];
        this.gameOver = false;
        moveNumber = 0;
        miniMax = new MiniMax(this.board, this);
        alphaBeta = new AlphaBeta(this.board, this);
        algoritm = miniMax;
    }

    public int getGameStatus() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int place = board[row][col];
                if (place != EMPTY) {
                    int player = board[row][col];
                    if (row < ROWS - 4) // look down
                        if (check(row, 1, col, 0))
                            return player;

                    if (col < COLS - 4) // look right
                        if (check(row, 0, col, 1))
                            return player;

                    if (col < COLS - 4 && row < ROWS - 4) // look diagonal right
                        if (check(row, 1, col, 1))
                            return player;
                    if (col > 3 && row < ROWS - 4)
                        if (check(row, 1, col, -1)) // look diagonal left
                            return player;
                }
            }
        }

        if (moveNumber == ROWS * COLS)
            return TIE;
        else
            return 0;
    }

    public void checkStatus() {
        if (!isGameOver()) {
            switch (getGameStatus()) {
                case 1:
                    setGameOver(true);
                    boardPanel.getWinningLabel().setText("Blacks wins!");
                    break;
                case 2:
                    setGameOver(true);
                    boardPanel.getWinningLabel().setText("Whites wins!");
                    break;
                case -1:
                    setGameOver(true);
                    boardPanel.getWinningLabel().setText("Draw!");
                    break;
            }
        }

        boardPanel.repaint();
    }

    public boolean check(int row, int rowDir, int col, int colDir) {
        int player = board[row][col];
        for (int i = 0; i < 5; i++) {
            if (board[row + rowDir * i][col + colDir * i] != player)
                return false;
        }
        return true;
    }

    public void moveHuman(int row, int col) {
        board[row][col] = nextPlayer;
        nextPlayer = 3 - nextPlayer;
        moveNumber++;
        checkStatus();
    }

    public void moveAI(boolean isOpponent) {
        if (!isGameOver()) {
            int[] move = algoritm.run(4, isOpponent);
            board[move[1]][move[2]] = nextPlayer;
            nextPlayer = 3 - nextPlayer;
            moveNumber++;
            checkStatus();
        }
    }

    public void move(int row, int col) {
        moveHuman(row, col);
        if (!areBothPlayers())
            moveAI(false);
    }

    public void firstAIMove() {
        board[7][7] = nextPlayer;
        nextPlayer = 3 - nextPlayer;
        moveNumber++;
    }

    public void setNextPlayer(int p) {
        nextPlayer = p;
    }

    public int getPlayerAt(int row, int col) {
        return board[row][col];
    }

    public boolean isTaken(int row, int col) {
        return board[row][col] == BLACK || board[row][col] == WHITE;
    }

    public void setHovered(int row, int col) {
        board[row][col] = 5 - nextPlayer;
    }

    public void setOldHovered(int x, int y) {
        oldHoveredX = x;
        oldHoveredY = y;
    }

    public void resetOldHovered() {
        if (getPlayerAt(oldHoveredX, oldHoveredY) == 3 || getPlayerAt(oldHoveredX, oldHoveredY) == 4)
            board[oldHoveredX][oldHoveredY] = 0;
    }

    public void reset() {
        if (AIGame != null)
            AIGame.interrupt();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = EMPTY;
            }
        }

        moveNumber = 0;
        gameOver = false;
        setNextPlayer(BLACK);
        boardPanel.setWinningLabelText("");
        boardPanel.repaint();

        if (blacksPlayer.equals("AI") && whitesPlayer.equals("Player"))
            firstAIMove();

        if (blacksPlayer.equals("AI") && whitesPlayer.equals("AI")) {
            AIGame = new Thread(
                    new Runnable() {
                        public void run() {
                            firstAIMove();
                            while (!Thread.currentThread().isInterrupted() && !isGameOver()) {
                                moveAI(false);
                                boardPanel.repaint();
                            }
                        }
                    }
            );
            AIGame.start();
        }
    }

    public int[][] cloneBoard() {
        int[][] copy = new int[ROWS][COLS];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                copy[i][j] = board[i][j];
        return copy;
    }

    public void setAlgoritm(String alg) {
        if (alg.equals("MiniMax")) {
            algoritm = miniMax;
        } else if (alg.equals("Alpha-Beta")) {
            algoritm = alphaBeta;
        }
    }

    public boolean areBothAI() {
        return blacksPlayer.equals("AI") && whitesPlayer.equals("AI");
    }

    public boolean areBothPlayers() {
        return blacksPlayer.equals("Player") && whitesPlayer.equals("Player");
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setHeuristic(String heuristic, boolean blacks) {
        algoritm.setHeuristic(heuristic, blacks);
    }

    public HeuristicInterface[] getHeuristics() {
        return algoritm.getHeuristic();
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public void setBlacksPlayer(String blacksPlayer) {
        this.blacksPlayer = blacksPlayer;
    }

    public void setWhitesPlayer(String whitesPlayer) {
        this.whitesPlayer = whitesPlayer;
    }

}
