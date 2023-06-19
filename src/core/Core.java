package core;

import graphics.UiCore;

public class Core {

    private World world = new World();
    private Player player = new Player();
    private UiCore uiCore = new UiCore(world, player);

    public Core() {
        uiCore.openWindow();

        while (true)
            uiCore.repaint();
    }

    public static void main(String[] args) {
        new Core();
    }
}
