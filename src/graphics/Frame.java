package graphics;

import javax.swing.*;

import java.awt.*;

import static graphics.UiValues.dimension;

/**
 * The Frame class represents a custom JFrame for the game window.
 */
public class Frame extends JFrame {

    /**
     * Constructs a Frame object with the specified title.
     *
     * @param title The title of the frame.
     */
    public Frame(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize((int) dimension.getWidth() / 2, (int) dimension.getHeight() / 2);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);
    }

    public void setInvisibleCursor(){
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageManager.getInvisibleCursor(), new Point(0, 0), "InvisibleCursor"));
    }
}
