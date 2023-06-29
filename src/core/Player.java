package core;

import core.player.Controller;

public class Player {

    private final World world;
    private final Controller controller;

    public Player(World world) {
        this.world = world;
        controller = new Controller(world);
    }

    public Controller getController(){
        return controller;
    }
}
