package Gomoku.Heuristics;

public interface HeuristicInterface {

    int evaluate(int player);
    void setBoard(int[][] board);
    String getName();
}
