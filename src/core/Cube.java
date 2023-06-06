package core;

import java.awt.*;

public class Cube {

    private Color color;

    private boolean isMirror = false;

    public Cube(Color color) {
        this.color = color;
    }

    public Cube(Color color, boolean isMirror) {
        this.color = color;
        this.isMirror = isMirror;
    }

    public static Cube createEmpty() {
        return new Cube(new Color(0, 0, 0, 0));
    }

    public static Cube createMirror() {
        return new Cube(Color.decode("#dedede"), true);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setMirror(boolean isMirror) {
        this.isMirror = isMirror;
    }

    public Color getColor() {
        return color;
    }

    public boolean isMirror() {
        return isMirror;
    }
}
