package SI3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BoardPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    JLabel winningLabel;
    int ROWS;
    int COLS;
    int CELL_SIZE;
    int space;
    private GameLogic gameLogic;
    final Color[] COLORS = {null, Color.black, Color.white,  new Color(255, 255, 255, 50), new Color(0, 0, 0, 50)};
    final int EMPTY = 0;


    BoardPanel() {
        winningLabel = new JLabel();
        winningLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        add(winningLabel);
        ROWS = 15;
        COLS = 15;
        CELL_SIZE = 30;
        space = CELL_SIZE / 2;
        setBackground(Color.orange);
        setOpaque(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        gameLogic = new GameLogic(ROWS, COLS, this);
        gameLogic.setNextPlayer(1);
    }

    public void checkStatus() {
        if(!gameLogic.isGameOver()) {
            switch(gameLogic.getGameStatus()) {
                case 1:
                    gameLogic.setGameOver(true);
                    winningLabel.setText("Player 1 WINS");
                    break;
                case 2:
                    gameLogic.setGameOver(true);
                    winningLabel.setText("Player 2 WINS");
                    break;
                case -1:
                    gameLogic.setGameOver(true);
                    winningLabel.setText("IT'S A DRAW");
                    break;
            }
        }
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < ROWS; i++) {
            g2d.drawLine(space, space + i * CELL_SIZE, space + (COLS - 1) * CELL_SIZE, space + i * CELL_SIZE);
        }

        for (int i = 0; i < COLS; i++) {
            g2d.drawLine(space + i * CELL_SIZE, space, space + i * CELL_SIZE, space + (ROWS - 1) * CELL_SIZE);
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                g2d.setColor(Color.black);
                int player = gameLogic.getPlayerAt(j, i);
                if (player != EMPTY) {
                    g2d.setColor(COLORS[player]);
                    g2d.fillOval(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / CELL_SIZE;
        int y = e.getY() / CELL_SIZE;
        if (!gameLogic.isGameOver() && x < COLS && y < ROWS && !gameLogic.isTaken(x, y)) {
            gameLogic.move(x, y);
            repaint();
            checkStatus();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gameLogic.resetOldHovered();
        int x = e.getX() / CELL_SIZE;
        int y = e.getY() / CELL_SIZE;
        if (x < 15 && y < 15 && !gameLogic.isTaken(x, y)) {
            gameLogic.setHovered(x, y);
            repaint();
            gameLogic.setOldHovered(x, y);
        }
    }

    protected GameLogic getGameLogic() {
        return gameLogic;
    }
}
