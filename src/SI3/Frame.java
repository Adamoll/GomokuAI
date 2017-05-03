package SI3;

import javax.swing.*;
import java.awt.*;


public class Frame extends JFrame {
    Frame() {
        super("Gomoku");
        MainPanel mainPanel = new MainPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(625, 460);
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(670, 490));
        setResizable(true);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }


    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}
