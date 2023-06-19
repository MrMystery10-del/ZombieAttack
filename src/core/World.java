package core;

import java.awt.*;
import java.util.ArrayList;

/**
 * The World class represents the game world consisting of cubes and zombies.
 */
public class World {

    private static final int X_AXE = 50;
    private static final int Y_AXE = 100;
    private static final int Z_AXE = 50;
    private final ArrayList<Zombie> zombies;
    private final int[][][] dimension;

    /**
     * Constructs a new World object and initializes the dimension array and zombies list.
     */
    public World() {
        zombies = new ArrayList<>();
        dimension = new int[X_AXE][Y_AXE][Z_AXE];

        initializeDimension();
        initializeBorders();
    }

    /**
     * Initializes the dimension array with empty cubes.
     */
    private void initializeDimension() {
        for (int x = 0; x < X_AXE; x++) {
            for (int y = 0; y < Y_AXE; y++) {
                for (int z = 0; z < Z_AXE; z++) {
                    dimension[x][y][z] = 0;
                }
            }
        }
    }

    /**
     * Initializes the border cubes in the dimension array.
     */
    private void initializeBorders() {
        for (int x = 0; x < 50; x++) {
            for (int z = 0; z < 50; z++) {
                dimension[x][0][z] = 0xFF825123;
                dimension[x][99][z] = 0xFFFFFFFF;
            }
        }

        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 100; y++) {
                dimension[x][y][0] = 0xFFFF0000;
                dimension[x][y][49] = 0xFFFF00FF;
            }
        }

        for (int z = 0; z < 50; z++) {
            for (int y = 0; y < 100; y++) {
                dimension[0][y][z] = 0xFF0000FF;
                dimension[49][y][z] = 0xFF00FF00;
            }
        }
    }

    /**
     * Updates the game world.
     */
    public void updateWorld() {
        // Update logic goes here
    }

    /**
     * Adds a zombie to the world.
     *
     * @param zombie The zombie to add.
     */
    public void addZombie(Zombie zombie) {
        zombies.add(zombie);
    }

    /**
     * Retrieves the list of zombies in the world.
     *
     * @return The list of zombies.
     */
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    /**
     * Retrieves the dimension array representing the game world.
     *
     * @return The dimension array.
     */
    public int[][][] getDimension() {
        return dimension;
    }
}
