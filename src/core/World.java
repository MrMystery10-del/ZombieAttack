package core;

import java.awt.*;
import java.util.ArrayList;

public class World {

    private static final int X_AXE = 50;
    private static final int Y_AXE = 50;
    private static final int Z_AXE = 100;
    private final ArrayList<Zombie> zombies;
    private final Cube[][][] dimension;

    public World() {
        zombies = new ArrayList<>();
        dimension = new Cube[X_AXE][Y_AXE][Z_AXE];

        for (int x = 0; x < X_AXE; x++) {
            for (int y = 0; y < Y_AXE; y++) {
                for (int z = 0; z < Z_AXE; z++) {
                    dimension[x][y][z] = Cube.createEmpty();
                }
            }
        }
    }

    public void updateWorld() {

    }

    private void clearWorld() {
        for (int x = 0; x < X_AXE; x++) {
            for (int y = 0; y < Y_AXE; y++) {
                for (int z = 0; z < Z_AXE; z++) {
                    dimension[x][y][z] = Cube.createEmpty();
                }
            }
        }
    }

    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public Cube[][][] getDimension() {
        return dimension;
    }
}
