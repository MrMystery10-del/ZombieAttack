package graphics;

import core.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is used to render a 3D dimension
 */
public class Render3D {

    private final static int ALPHA_MASK = 0xFF;
    private final static int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
    private BufferedImage resultImage;
    private Graphics2D resultImageGraphics;

    private int[] cubes;
    private Graphics2D screen;
    private int width, height;
    private double cameraX, cameraY, cameraZ, rotationX, rotationY;

    /**
     * This method should only be called when a world update happened (example: player shoot, zombie walked)
     */
    public void parseWorldData(int[] cubes, int width, int height) {
        this.cubes = cubes;
        this.width = width;
        this.height = height;

        updateImageData();
    }

    /**
     * This method should be called on each tick before render the frame to provide up-to-date vision
     */
    public void parseTickData(Graphics2D screen, double cameraX, double cameraY, double cameraZ, double rotationX, double rotationY) {
        this.screen = screen;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.cameraZ = cameraZ;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
    }

    /**
     * Renders a full frame based on the provided player camera
     */
    public void renderFrame() throws InterruptedException {
        try {
            int i = 0;
            final double FOV_HALF = UiValues.getFOV() / 2;

            final double START = rotationX - FOV_HALF;
            final double END = rotationX + FOV_HALF;
            final double STEP = UiValues.getFOV() / UiValues.getRaysX();

            List<Callable<Void>> tasks = new ArrayList<>(UiValues.getRaysX());
            for (double x = START; x < END; x += STEP) {
                double finalX = x;
                int finalI = i;

                tasks.add(() -> {
                    renderCol(finalX, finalI);
                    return null;
                });

                i++;
            }
            executor.invokeAll(tasks);
        } finally {
            // Draw the output frame on the user's screen/panel
            screen.drawImage(resultImage, 0, 0, width, height, null);

            // Reset the executor for the next frame
            executor = Executors.newFixedThreadPool(NUM_THREADS);
        }
    }

    // Render column of x
    private void renderCol(double x, int i) {
        int j = 0;

        final double FOV_HALF = UiValues.getyFov() / 2;

        final double START = rotationY + FOV_HALF;
        final double END = rotationY - FOV_HALF;
        final double STEP = UiValues.getyFov() / UiValues.getRaysY();
        for (double y = START; y > END; y -= STEP) {
            double degreeX = Math.toRadians(x);
            double degreeY = Math.toRadians(y);

            double sinY = Math.sin(degreeY);

            double dirX = Math.cos(degreeX) * sinY;
            double dirY = -Math.cos(degreeY);
            double dirZ = -Math.sin(degreeX) * sinY;

            Color color = rayCast(cameraX, cameraY, cameraZ, dirX, dirY, dirZ);

            resultImageGraphics.setColor(color);
            resultImageGraphics.fillRect(width / UiValues.getRaysX() * i, height / UiValues.getRaysY() * j, width / UiValues.getRaysX(), height / UiValues.getRaysY());

            j++;
        }
    }

    /**
     * Performs ray casting in the virtual world to determine the color of the ray.
     *
     * @param rayX The initial x-coordinate of the ray.
     * @param rayY The initial y-coordinate of the ray.
     * @param rayZ The initial z-coordinate of the ray.
     * @param dirX The x-direction of the ray.
     * @param dirY The y-direction of the ray.
     * @param dirZ The z-direction of the ray.
     * @return The resulting color of the ray after ray casting.
     */
    private Color rayCast(double rayX, double rayY, double rayZ, double dirX, double dirY, double dirZ) {

        int rayValue = 0;
        int travel = 0;

        int cubeX, cubeY, cubeZ;
        int cube, factor;

        while ((rayValue >> 24 & ALPHA_MASK) < ALPHA_MASK) {
            // Get current cube position of where ray is located
            cubeX = (int) rayX;
            cubeY = (int) rayY;
            cubeZ = (int) rayZ;

            // Shoot ray further
            rayX += dirX;
            rayY += dirY;
            rayZ += dirZ;

            // Used for shadow effect
            travel += 2;

            // Stops the ray after darkness level met/reduce long range shoots
            if (travel > 230) {
                return new Color(0, 0, 0);
            }

            cube = cubes[cubeX * World.X_AXE * World.Y_AXE + cubeY * World.Z_AXE + cubeZ];
            factor = cube >> 24 & ALPHA_MASK;

            // If empty cube skip calculations and go further
            if (factor == 0) {
                continue;
            }

            // When ray hits a cube it mixes the color
            rayValue = mixColor(rayValue, cube, ALPHA_MASK - (float) (rayValue >> 24 & ALPHA_MASK) / factor);
        }

        int r = Math.max(0, (rayValue >> 16 & ALPHA_MASK) - travel);
        int g = Math.max(0, (rayValue >> 8 & ALPHA_MASK) - travel);
        int b = Math.max(0, (rayValue & ALPHA_MASK) - travel);

        return new Color(r, g, b);
    }

    // Mixes the color based on the factor(alpha)
    private int mixColor(int main, int addition, float factor) {

        // Ensure factor is between 0 and 1
        factor = factor > 1 ? 1 : factor;

        // Extract alpha channel from main color
        int mainAlpha = main >> 24 & ALPHA_MASK;

        // Calculate blended alpha value
        int blendedAlpha = (mainAlpha + (int) (factor * ALPHA_MASK)) & ALPHA_MASK;

        // Extract RGB channels from main and addition colors
        int mainRed = main >> 16 & ALPHA_MASK;
        int mainGreen = main >> 8 & ALPHA_MASK;
        int mainBlue = main & ALPHA_MASK;
        int additionRed = addition >> 16 & ALPHA_MASK;
        int additionGreen = addition >> 8 & ALPHA_MASK;
        int additionBlue = addition & ALPHA_MASK;

        // Calculate blended RGB values
        int blendedRed = (mainRed * (ALPHA_MASK - (int) (factor * ALPHA_MASK)) + additionRed * (int) (factor * ALPHA_MASK)) >> 8;
        int blendedGreen = (mainGreen * (ALPHA_MASK - (int) (factor * ALPHA_MASK)) + additionGreen * (int) (factor * ALPHA_MASK)) >> 8;
        int blendedBlue = (mainBlue * (ALPHA_MASK - (int) (factor * ALPHA_MASK)) + additionBlue * (int) (factor * ALPHA_MASK)) >> 8;

        return blendedAlpha << 24 | blendedRed << 16 | blendedGreen << 8 | blendedBlue;
    }


    // updates the image data
    private void updateImageData() {
        resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        resultImageGraphics = resultImage.createGraphics();
    }
}
