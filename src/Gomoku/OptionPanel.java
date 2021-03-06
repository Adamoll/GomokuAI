package Gomoku;

import Gomoku.Heuristics.HeuristicInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class OptionPanel extends JPanel implements ActionListener {
    private final String[] players = {"Player", "AI"};
    private final String[] AIHeuristics = {"Line", "Blocking", "Spatial"};
    private Color backgroundColor;
    private JComboBox blacksPlayer;
    private JComboBox whitesPlayer;
    private JComboBox AI1;
    private JComboBox AI2;
    private JButton startButton;
    private JRadioButton miniMax;
    private JRadioButton alphaBeta;
    private ButtonGroup algoritmGroup;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel algoritmLabel;

    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private GameLogic gameLogic;

    OptionPanel(GameLogic gameLogic) {
        super();
        this.gameLogic = gameLogic;

        backgroundColor = Color.yellow;
        setBackground(backgroundColor);
        setOpaque(true);

        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(layout);

        AI1 = new JComboBox(AIHeuristics);
        AI2 = new JComboBox(AIHeuristics);
        blacksPlayer = new JComboBox(players);

        gameLogic.setBlacksPlayer(players[0]);
        whitesPlayer = new JComboBox(players);
        whitesPlayer.setSelectedIndex(1);

        gameLogic.setWhitesPlayer(players[1]);

        RadioButtonActionListener radioButtonActionListener = new RadioButtonActionListener();
        algoritmLabel = new JLabel("Algoritm:");

        miniMax = new JRadioButton("MiniMax");
        miniMax.setBackground(backgroundColor);
        miniMax.addActionListener(radioButtonActionListener);

        alphaBeta = new JRadioButton("Alpha-Beta");
        alphaBeta.setBackground(backgroundColor);
        alphaBeta.addActionListener(radioButtonActionListener);
        alphaBeta.setSelected(true);

        algoritmGroup = new ButtonGroup();
        algoritmGroup.add(miniMax);
        algoritmGroup.add(alphaBeta);

        player1Label = new JLabel("Black");
        player2Label = new JLabel("White");
        startButton = new JButton("Start game");

        addListeners();
        buildLayout();

        AI1.setVisible(false);
    }

    public void addListeners() {
        blacksPlayer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) event.getItem();
                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    gbc.anchor = GridBagConstraints.NORTH;
                    remove(blacksPlayer);
                    if (item.equals("AI")) {
                        gbc.insets = new Insets(0, 0, 0, 0);
                        AI1.setVisible(true);
                    } else {
                        gbc.insets = new Insets(0, 0, AI1.getHeight(), 0);
                        AI1.setVisible(false);
                    }
                    gameLogic.setBlacksPlayer(item);
                    add(blacksPlayer, gbc);
                }
            }
        });

        whitesPlayer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) event.getItem();
                    gbc.gridx = 0;
                    gbc.gridy = 7;
                    gbc.anchor = GridBagConstraints.NORTH;
                    remove(whitesPlayer);
                    if (item.equals("AI")) {
                        gbc.insets = new Insets(0, 0, 0, 0);
                        AI2.setVisible(true);
                    } else {
                        gbc.insets = new Insets(0, 0, AI2.getHeight(), 0);
                        AI2.setVisible(false);
                    }
                    gameLogic.setWhitesPlayer(item);
                    add(whitesPlayer, gbc);
                }
            }
        });

        AI1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) event.getItem();
                    setHeuristic(item, true);
                }
            }
        });

        AI2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) event.getItem();
                    setHeuristic(item, false);
                }
            }
        });

        startButton.addActionListener(this);
    }

    public void buildLayout() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(algoritmLabel, gbc);
        gbc.gridy++;
        add(alphaBeta, gbc);
        gbc.gridy++;
        add(miniMax, gbc);
        gbc.gridy++;
        add(player1Label, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 25, 0);
        add(blacksPlayer, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy++;
        add(AI1, gbc);
        gbc.gridy++;
        add(player2Label, gbc);
        gbc.gridy++;
        add(whitesPlayer, gbc);
        gbc.gridy++;
        add(AI2, gbc);
        gbc.gridy++;
        add(startButton, gbc);
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

    private void setHeuristic(String heuristic, boolean blacks) {
        gameLogic.setHeuristic(heuristic, blacks);
    }

    private class RadioButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            JRadioButton button = (JRadioButton) event.getSource();
            HeuristicInterface[] heuristics = gameLogic.getHeuristics();
            gameLogic.setAlgoritm(button.getText());
            gameLogic.setHeuristic(heuristics[0].getName(), true);
            gameLogic.setHeuristic(heuristics[1].getName(), false);
        }
    }
}

