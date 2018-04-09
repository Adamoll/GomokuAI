# GomokuAI
**GomokuAI** is implementation of Gomoku (known as well as Five in a row) featuring AI approach based on two algorithms: **Min-Max** and **α-β pruning**.
 
Gomoku is abstract strategy board game, where board consists of 15x15 grid intersections.
Player alternate turns placing a stone of their color on an empty intersection. The winner is the first player to form an unbroken chain of five stones horizontally, vertically or diagonally.

AI can use one of three heuristics to evaluate state of the game to choose the best possible move at the moment:
  - **Line heuristic** - AI tends to build patch of 5 stones as fast as possible, no matter of opponent moves,
  - **Spatial heuristic** - AI puts stones in places where stone would be as close as possible to the other stones on the board,
  in other words, in places where stone has the most neighbors of its color.
  - **Blocking heuristic** - Similar to line heuristic, but AI takes into consideration possible opponent moves and blocks him,
  if it would give him advantage. This is the strongest one, so you can test your skills! :D
  
**GomokuAI** can also simulate game between two AI players with different heuristics.
