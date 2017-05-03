package SI3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class OptionPanel extends JPanel implements ActionListener {

    JComboBox blacksPlayer;
    JComboBox whitesPlayer;
    JComboBox AI1;
    JComboBox AI2;
    JLabel player1Label;
    JLabel player2Label;
    JButton startButton;
    final String[] players = {"Player", "AI"};
    final String[] AIHeuristics = {"Line", "Blocking"};
    GridBagLayout layout;
    GridBagConstraints gbc;
    private GameLogic gameLogic;

    OptionPanel(GameLogic gameLogic) {
        super();
        this.gameLogic = gameLogic;

        setBackground(Color.cyan);
        setOpaque(true);
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(layout);
        AI1 = new JComboBox(AIHeuristics);
        AI2 = new JComboBox(AIHeuristics);
        blacksPlayer = new JComboBox(players);
        blacksPlayer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) event.getItem();
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.anchor = GridBagConstraints.NORTH;
                    remove(blacksPlayer);
                    if (item.equals("AI")) {
                        gbc.insets = new Insets(0, 0, 0, 0);
                        AI1.setVisible(true);
                    } else {
                        gbc.insets = new Insets(0, 0, AI1.getHeight(), 0);
                        AI1.setVisible(false);
                    }
                    add(blacksPlayer, gbc);
                }
            }
        });
        whitesPlayer = new JComboBox(players);
        whitesPlayer.setSelectedIndex(1);
        whitesPlayer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) event.getItem();
                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    gbc.anchor = GridBagConstraints.NORTH;
                    remove(whitesPlayer);
                    if (item.equals("AI")) {
                        gbc.insets = new Insets(0, 0, 0, 0);
                        AI2.setVisible(true);
                    } else {
                        gbc.insets = new Insets(0, 0, AI2.getHeight(), 0);
                        AI2.setVisible(false);
                    }
                    add(whitesPlayer, gbc);
                }
            }
        });

        AI1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) event.getItem();
                    setHeuristic(item);
                }
            }
        });

        player1Label = new JLabel("Black");
        player2Label = new JLabel("White");
        startButton = new JButton("Start game");
        startButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        add(player1Label, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        add(blacksPlayer, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 2;
        add(AI1, gbc);
        gbc.gridy = 3;
        add(player2Label, gbc);
        gbc.gridy = 4;
        add(whitesPlayer, gbc);
        gbc.gridy = 5;
        add(AI2, gbc);
        gbc.gridy = 6;
        add(startButton, gbc);

        AI1.setVisible(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            gameLogic.reset();
        }
    }

    private void setHeuristic(String heuristic) {
        gameLogic.setHeuristic(heuristic);
    }
}
