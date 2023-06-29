package core;

import graphics.UiCore;
import objects.spaces.Room;

public class Core {

    private World world = new World();
    private Player player = new Player();
    private UiCore uiCore = new UiCore(world, player);

    public Core() {
        Room room1 = new Room(0xFFD9D78B, 0xFF96953C, 0xFFD9D78B);

        world.addNewObject(room1.createMash(100, 30, 100), 0, 0, 0);
        world.addNewObject(room1.createMash(10, 15, 10), 10, 0, 40);

        uiCore.openWindow();

        uiCore.updateWorldData();

        while (true)
            uiCore.repaint();
    }

    public static void main(String[] args) {
        new Core();
    }
}
