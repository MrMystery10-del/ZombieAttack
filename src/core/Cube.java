package core;

import java.awt.*;

/**
 * The Cube class represents a cube in the game world.
 */
public class Cube {

    private Color color;
    private boolean isMirror;

    /**
     * Constructs a Cube object with the specified color.
     *
     * @param color The color of the cube.
     */
    public Cube(Color color) {
        this.color = color;
        this.isMirror = false;
    }

    /**
     * Constructs a Cube object with the specified color and mirror property.
     *
     * @param color    The color of the cube.
     * @param isMirror Indicates if the cube is a mirror.
     */
    public Cube(Color color, boolean isMirror) {
        this.color = color;
        this.isMirror = isMirror;
    }

    /**
     * Creates an empty cube with no color (transparent).
     *
     * @return The empty cube.
     */
    public static Cube createEmpty() {
        return new Cube(new Color(0, 0, 0, 0), false);
    }

    /**
     * Creates a mirror cube with a predefined color.
     *
     * @return The mirror cube.
     */
    public static Cube createMirror() {
        return new Cube(Color.decode("#dedede"), true);
    }

    /**
     * Sets the color of the cube.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the mirror property of the cube.
     *
     * @param isMirror Indicates if the cube is a mirror.
     */
    public void setMirror(boolean isMirror) {
        this.isMirror = isMirror;
    }

    /**
     * Retrieves the color of the cube.
     *
     * @return The color of the cube.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Checks if the cube is a mirror.
     *
     * @return true if the cube is a mirror, false otherwise.
     */
    public boolean isMirror() {
        return isMirror;
    }
}
