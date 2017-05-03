package SI3;


public class GameLogic {
    private int ROWS;
    private int COLS;
    private int[][] board;
    private int nextPlayer;
    private int moveNumber;
    private int oldHoveredX;
    private int oldHoveredY;
    private BoardPanel boardPanel;

    static final int BLACK = 1;
    static final int WHITE = 2;
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


    public void move(int row, int col) {
        board[row][col] = nextPlayer;
        nextPlayer = 3 - nextPlayer;
        moveNumber++;
        int[] move = miniMax.miniMax( 2, false);
        board[move[1]][move[2]] = nextPlayer;
        nextPlayer = 3 - nextPlayer;
        moveNumber++;
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
        boardPanel.winningLabel.setText("");
        boardPanel.repaint();

    }

    public int[][] cloneBoard() {
        int[][] copy = new int[ROWS][COLS];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                copy[i][j] = board[i][j];
        return copy;
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    protected void setHeuristic(String heuristic) {
        miniMax.setHeuristic(heuristic);
    }
}
