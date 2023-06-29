package objects.spaces;

import objects.Mesh;

public class Room {

    private int floorColor, wallColor, ceilingColor;

    /**
     * Creates a new room in provided color for all walls
     */
    public Room(int color) {
        floorColor = color;
        wallColor = color;
        ceilingColor = color;
    }

    /**
     * Creates a new room in provided color for all walls
     */
    public Room(int floorColor, int wallColor, int ceilingColor) {
        this.floorColor = floorColor;
        this.wallColor = wallColor;
        this.ceilingColor = ceilingColor;
    }

    public Mesh createMash(int sizeX, int sizeY, int sizeZ) {
        int[] mesh = new int[sizeX * sizeY * sizeZ];

        // Floor and ceiling
        for (int x = 0; x < sizeX; x++) {
            for (int z = 0; z < sizeZ; z++) {
                mesh[x * sizeX * sizeY + z] = floorColor;
                mesh[x * sizeX * sizeY + (sizeY - 1) * sizeZ + z] = ceilingColor;
            }
        }

        // Walls
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                mesh[x * sizeX * sizeY + y * sizeZ] = wallColor;
                mesh[x * sizeX * sizeY + y * sizeZ + (sizeZ - 1)] = wallColor;
            }
        }
        for (int z = 0; z < sizeZ; z++) {
            for (int y = 0; y < sizeY; y++) {
                mesh[y * sizeZ + z] = wallColor;
                mesh[(sizeX - 1) * sizeX * sizeY + y * sizeZ + z] = wallColor;
            }
        }

        return new Mesh(mesh, sizeX, sizeY, sizeZ);
    }
}
