package Gomoku.Heuristics;

/**
 * Created by Jan Matejko on 02.05.2017.
 */
public interface HeuristicInterface {

    int evaluate(int player);
    void setBoard(int[][] board);
    String getName();
}
