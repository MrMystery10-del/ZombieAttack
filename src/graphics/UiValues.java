package graphics;

import java.awt.*;

public class UiValues {

    public final static String title = "ZombieAttack";
    public final static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    private static double FOV = 70;
    private static double Y_FOV = FOV / (16 / 9.0);

    private static int raysX = 384;
    private static int raysY = 339;

    public static void setFOV(double FOV) {
        UiValues.FOV = FOV;
    }

    public static void setRaysX(int raysX) {
        UiValues.raysX = raysX;
    }

    public static void setRaysY(int raysY) {
        UiValues.raysY = raysY;
    }

    public static double getFOV() {
        return FOV;
    }

    public static double getyFov() {
        return Y_FOV;
    }

    public static int getRaysX() {
        return raysX;
    }

    public static int getRaysY() {
        return raysY;
    }
}
