package graphics;

import javax.swing.*;

import static graphics.UiConstants.dimension;

public class Frame extends JFrame {

    public Frame(String title, JPanel panel){
        setTitle(title);

        setSize((int) dimension.getWidth() / 2, (int) dimension.getHeight() / 2);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(panel);
    }
}
