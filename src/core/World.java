package core;

import objects.Mesh;

import java.util.ArrayList;

/**
 * The World class represents the game world consisting of cubes and zombies.
 */
public class World {

    public final static int X_AXE = 100;
    public final static int Y_AXE = 30;
    public final static int Z_AXE = 100;

    private final ArrayList<Zombie> zombies;
    private final int[] dimension;

    /**
     * Constructs a new World object and initializes the dimension array and zombies list.
     */
    public World() {
        zombies = new ArrayList<>();
        dimension = new int[X_AXE * Y_AXE * Z_AXE];

        initializeDimension();

        for (int x = 20; x < 28; x++) {
            for (int y = 0; y < 20; y++) {
                dimension[x * X_AXE * Y_AXE + y * Z_AXE + 30] = 0xFF2DA65F;
            }
        }
    }

    /**
     * Initializes the dimension array with empty cubes.
     */
    private void initializeDimension() {
        for (int x = 0; x < X_AXE; x++) {
            for (int y = 0; y < Y_AXE; y++) {
                for (int z = 0; z < Z_AXE; z++) {
                    dimension[x * X_AXE * Y_AXE + y * Z_AXE + z] = 0;
                }
            }
        }
    }

    public void addNewObject(Mesh mesh, int posX, int posY, int posZ) {

        for (int x = posX; x < mesh.sizeX() + posX; x++) {
            for (int y = posY; y < mesh.sizeY() + posY; y++) {
                for (int z = posZ; z < mesh.sizeZ() + posZ; z++) {

                    if (x < X_AXE && y < Y_AXE && z < Z_AXE) {
                        int i = x - posX;
                        int j = y - posY;
                        int k = z - posZ;

                        dimension[x * X_AXE * Y_AXE + y * Z_AXE + z] = mesh.mesh()[i * mesh.sizeX() * mesh.sizeY() + j * mesh.sizeZ() + k];
                    }
                }
            }
        }
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
    public int[] getDimension() {
        return dimension;
    }
}
