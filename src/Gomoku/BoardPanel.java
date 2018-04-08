package Gomoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private final int EMPTY = 0;
    private final Color[] COLORS = {null, Color.black, Color.white,
            new Color(255, 255, 255, 80),
            new Color(0, 0, 0, 80)};
    private int ROWS;
    private int COLS;
    private int CELL_SIZE;
    private int space;
    private JLabel winningLabel;
    private GameLogic gameLogic;

    BoardPanel() {
        ROWS = 15;
        COLS = 15;
        CELL_SIZE = 30;
        space = CELL_SIZE / 2;

        gameLogic = new GameLogic(ROWS, COLS, this);
        gameLogic.setNextPlayer(GameLogic.BLACK);

        winningLabel = new JLabel();
        winningLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        add(winningLabel);
        setBackground(Color.orange);
        setOpaque(true);
        addMouseListener(this);
        addMouseMotionListener(this);
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
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / CELL_SIZE;
        int y = e.getY() / CELL_SIZE;
        if (!gameLogic.areBothAI()) {
            if (!gameLogic.isGameOver() && x < COLS && y < ROWS && !gameLogic.isTaken(x, y)) {
                gameLogic.move(x, y);
                repaint();
            }
        }
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

    public void setWinningLabelText(String text) {
        winningLabel.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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

    protected GameLogic getGameLogic() {
        return gameLogic;
    }

    public JLabel getWinningLabel() {
        return winningLabel;
    }
}
