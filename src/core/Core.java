package core;

import graphics.UiCore;
import objects.spaces.Room;

public class Core {

    private World world = new World();
    private Player player = new Player(world);
    private UiCore uiCore = new UiCore(world, player);

    public Core() {
        Room room1 = new Room(0xFFD9D78B, 0xFF96953C, 0xFFD9D78B);

        world.addNewObject(room1.createMash(100, 30, 100), 0, 0, 0);
        world.addNewObject(room1.createMash(10, 15, 20), 15, 0, 70);
        world.addNewObject(room1.createMash(10, 12, 15), 65, 17, 20);
        world.addNewObject(room1.createMash(8, 15, 9), 92, 5, 23);
        world.addNewObject(room1.createMash(3, 3, 100), 44, 13, 0);

        uiCore.openWindow();

        uiCore.updateWorldData();

        while (true)
            uiCore.repaint();
    }

    public static void main(String[] args) {
        new Core();
    }
}
