package graphics;

import core.Cube;

import java.awt.*;

public class Render3D {

    public static void render(Graphics2D graphics2D, Cube[][][] cubes, int width, int height, double cameraX, double cameraY, double cameraZ, double rX, double rY) {
        double rayX = cameraX;
        double rayY = cameraY;
        double rayZ = cameraZ;

        double dirX = Math.sin(rX) * Math.cos(rY);
        double dirY = Math.sin(rY);
        double dirZ = -Math.cos(rX) * Math.cos(rY);

        rayCast(cubes, rayX, rayY, rayZ, dirX, dirY, dirZ);
    }

    private static Color rayCast(Cube[][][] cubes, double rayX, double rayY, double rayZ, double dirX, double dirY, double dirZ) {

        Cube tempCube = cubes[(int) Math.floor(rayX)][(int) Math.floor(rayY)][(int) Math.floor(rayZ)];

        int thickness = tempCube.getColor().getAlpha();
        Color rayValue = tempCube.getColor();

        while (rayX >= 0 && rayX < cubes.length && rayY >= 0 && rayY < cubes[0].length && rayZ >= 0 && rayZ < cubes[0][0].length && thickness < 255) {
            int cubeX = (int) Math.floor(rayX);
            int cubeY = (int) Math.floor(rayY);
            int cubeZ = (int) Math.floor(rayZ);

            rayX += dirX;
            rayY += dirY;
            rayZ += dirZ;

            Cube cube = cubes[cubeX][cubeY][cubeZ];

            int amount = cube.getColor().getAlpha();

            if (amount == 0) continue;

            rayValue = mixColor(rayValue, cube.getColor(), amount * 100 / thickness);
            thickness += amount;
        }
        return rayValue;
    }

    private static Color mixColor(Color main, Color addition, float amount) {
        return new Color(
                (int) (main.getRed() * (1f - amount) + addition.getRed() * amount),
                (int) (main.getGreen() * (1f - amount) + addition.getGreen() * amount),
                (int) (main.getBlue() * (1f - amount) + addition.getBlue() * amount)
        );
    }
}
