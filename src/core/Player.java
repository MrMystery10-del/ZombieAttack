package core;

import core.player.Controller;

public class Player {

    private final Controller controller = new Controller();

    public Controller getController(){
        return controller;
    }
}
