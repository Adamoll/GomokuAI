package Gomoku;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    GridBagLayout gridBagLayout;
    GridBagConstraints gridBagConstraints;
    OptionPanel optionPanel;
    BoardPanel boardPanel;

    MainPanel() {
        super();
        boardPanel = new BoardPanel();
        optionPanel = new OptionPanel(boardPanel.getGameLogic());
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        setLayout(gridBagLayout);
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        add(boardPanel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        add(optionPanel, gridBagConstraints);
    }
}
