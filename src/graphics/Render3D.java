package graphics;

import core.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Render3D {

    private final static int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private final int[][][] cubes;
    private final Graphics2D screen;
    private final int width, height;
    private final double cameraX, cameraY, cameraZ, rotationX, rotationY;

    public Render3D(int[][][] cubes, Graphics2D screen, int width, int height, double cameraX, double cameraY, double cameraZ, double rotationX, double rotationY) {
        this.cubes = cubes;
        this.screen = screen;
        this.width = width;
        this.height = height;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.cameraZ = cameraZ;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
    }

    public void render() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_555_RGB);
        Graphics2D resultImageGraphics = resultImage.createGraphics();

        try {
            int i = 0;
            for (double x = rotationX - UiValues.getFOV() / 2; x < rotationX +  UiValues.getFOV() / 2; x +=  UiValues.getFOV() / UiValues.getRaysX()) {
                double finalX = x;
                int finalI = i;

                executor.submit(() -> renderCol(resultImageGraphics, finalX, finalI));
                i++;
            }

        } finally {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }

        screen.drawImage(resultImage, 0, 0, width, height, null);
    }

    private void renderCol(Graphics2D resultImageGraphics, double x, int i) {
        int j = 0;
        double yFOV = UiValues.getFOV() / (16 / 9);
        for (double y = rotationY + yFOV / 2; y > rotationY - yFOV / 2; y -= yFOV / UiValues.getRaysY()) {
            double degreeX = Math.toRadians(x);
            double degreeY = Math.toRadians(y);
            double dirX = Math.cos(degreeX) * Math.sin(degreeY);
            double dirY = -Math.cos(degreeY);
            double dirZ = -Math.sin(degreeX) * Math.sin(degreeY);
            Color color = rayCast(cubes, cameraX, cameraY, cameraZ, dirX, dirY, dirZ);

            resultImageGraphics.setColor(color);
            resultImageGraphics.fillRect((int) (i * (width / (UiValues.getRaysX() * 1.0))), (int) (j * (height / (UiValues.getRaysY() * 1.0))), (int) (width / (UiValues.getRaysX() * 1.0)), (int) (height / (UiValues.getRaysY() * 1.0)));
            j++;
        }
    }

    private static Color rayCast(int[][][] cubes, double rayX, double rayY, double rayZ, double dirX, double dirY, double dirZ) {
        int rayValue = 0x00000000;
        double travel = 0;

        while (World.isInBorder(rayX, rayY, rayZ) && (rayValue >> 24 & 0xFF) < 0xFF) {
            int cubeX = (int) rayX;
            int cubeY = (int) rayY;
            int cubeZ = (int) rayZ;

            rayX += dirX;
            rayY += dirY;
            rayZ += dirZ;

            travel += 2.8;

            int cube = cubes[cubeX][cubeY][cubeZ];

            int amount = cube >> 24 & 0xFF;

            if (amount == 0) {
                continue;
            }

            rayValue = mixColor(rayValue, cube, amount / (255 - (rayValue >> 24 >>> 24)));
            int t = rayValue >> 24 & 0xFF + amount;
            int r = rayValue >> 16 & 0xFF;
            int g = rayValue >> 8 & 0xFF;
            int b = rayValue & 0xFF;

            rayValue = t << 24 | r << 16 | g << 8 | b;
        }

        int r = (int) Math.max(0, (rayValue >> 16 & 0xFF) - travel);
        int g = (int) Math.max(0, (rayValue >> 8 & 0xFF) - travel);
        int b = (int) Math.max(0, (rayValue & 0xFF) - travel);

        return new Color(r, g, b);
    }

    private static int mixColor(int main, int addition, float amount) {
        if (amount > 1) amount = 1;

        int r = (int) ((main >> 16 & 0xFF) * (1f - amount) + (addition >> 16 & 0xFF) * amount);
        int g = (int) ((main >> 8 & 0xFF) * (1f - amount) + (addition >> 8 & 0xFF) * amount);
        int b = (int) ((main & 0xFF) * (1f - amount) + (addition & 0xFF) * amount);

        return r << 16 | g << 8 | b;
    }
}
