package Gomoku;

import Gomoku.Heuristics.HeuristicInterface;

public interface SearchAlgoritm {
    int[] run(int depth, boolean isOpponent);
    void setHeuristic(String heuristic, boolean blacks);
    HeuristicInterface[] getHeuristic();
}
