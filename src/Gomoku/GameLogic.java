package Gomoku;


public class GameLogic {
    private int ROWS;
    private int COLS;
    private int[][] board;
    private int nextPlayer;
    private int moveNumber;
    private int oldHoveredX;
    private int oldHoveredY;
    private BoardPanel boardPanel;
    String blacksPlayer;
    String whitesPlayer;
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    static final int EMPTY = 0;
    private final int TIE = -1;
    private boolean gameOver;
    private MiniMax miniMax;


    GameLogic(int ROWS, int COLS, BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.board = new int[ROWS][COLS];
        this.gameOver = false;
        moveNumber = 0;
        miniMax = new MiniMax(this.board, this);
    }

    public int getPlayerAt(int row, int col) {
        return board[row][col];
    }

    public void setHovered(int row, int col) {
        board[row][col] = 5 - nextPlayer;
    }


    public void firstAIMove() {
        board[7][7] = nextPlayer;
        nextPlayer = 3 - nextPlayer;
        moveNumber++;
    }

    public void moveHuman(int row, int col) {
        board[row][col] = nextPlayer;
        nextPlayer = 3 - nextPlayer;
        moveNumber++;
        checkStatus();
    }

    public void moveAI(boolean isOpponent) {
        if (!isGameOver()) {
            int[] move = miniMax.run(2, isOpponent);
            board[move[1]][move[2]] = nextPlayer;
            nextPlayer = 3 - nextPlayer;
            moveNumber++;
            checkStatus();
        }
    }


    public void move(int row, int col) {
        moveHuman(row, col);
        if(!areBothPlayers())
            moveAI(false);
    }


    public void setNextPlayer(int p) {
        nextPlayer = p;
    }

    public boolean isTaken(int row, int col) {
        return board[row][col] != 0 && board[row][col] != 3 && board[row][col] != 4;
    }

    public void setOldHovered(int x, int y) {
        oldHoveredX = x;
        oldHoveredY = y;
    }

    public void resetOldHovered() {
        if (getPlayerAt(oldHoveredX, oldHoveredY) == 3 || getPlayerAt(oldHoveredX, oldHoveredY) == 4)
            board[oldHoveredX][oldHoveredY] = 0;
    }

    public boolean check(int row, int rowDir, int col, int colDir) {
        int player = board[row][col];
        for (int i = 0; i < 5; i++) {
            if (board[row + rowDir * i][col + colDir * i] != player)
                return false;
        }
        return true;
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

    public int getGameStatus() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int place = board[row][col];
                if (place != EMPTY) {
                    int player = board[row][col];
                    if (row < ROWS - 4) // look upp
                        if (check(row, 1, col, 0))
                            return player;

                    if (col < COLS - 4) // look right
                        if (check(row, 0, col, 1))
                            return player;

                    if (col < COLS - 4 && row < ROWS - 4) // look diagonal right
                        if (check(row, 1, col, 1))
                            return player;
                    if (col > 3 && row < ROWS - 4)
                        if (check(row, 1, col, -1))
                            return player;

                }
            }
        }
        if (moveNumber == ROWS * COLS)
            return TIE;
        else
            return 0;
    }

    public void reset() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = EMPTY;
            }
        }
        moveNumber = 0;
        gameOver = false;
        setNextPlayer(BLACK);
        boardPanel.winningLabel.setText("");
        boardPanel.repaint();

        if (blacksPlayer.equals("AI") && whitesPlayer.equals("Player"))
            firstAIMove();

        if (blacksPlayer.equals("AI") && whitesPlayer.equals("AI")) {
            new Thread(
                    new Runnable() {
                        public void run() {
                            firstAIMove();
                            while (!isGameOver()) {
                                moveAI(false);
                                boardPanel.repaint();
                            }
                        }
                    }
            ).start();
        }
    }

    public int[][] cloneBoard() {
        int[][] copy = new int[ROWS][COLS];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                copy[i][j] = board[i][j];
        return copy;
    }

    public boolean isPlayer() {
        return blacksPlayer.equals("Player") || whitesPlayer.equals("Player");
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
        miniMax.setHeuristic(heuristic, blacks);
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
