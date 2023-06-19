package core.player;

import core.Tuple;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controller {

    private final Camera camera = new Camera();
    private final Tuple<Integer, Integer, Integer> position = new Tuple<>(25, 1, 25);
    private final Action goForward = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    };

    private final Action goBack = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    };

    private final Action goLeft = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    };

    private final Action goRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    };

    private final Action shoot = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("shoot");
        }
    };

    private final Tuple[] actions = new Tuple[]{
            new Tuple<>(KeyStroke.getKeyStroke("W"), "goForward", goForward),
            new Tuple<>(KeyStroke.getKeyStroke("S"), "goBack", goBack),
            new Tuple<>(KeyStroke.getKeyStroke("A"), "goLeft", goLeft),
            new Tuple<>(KeyStroke.getKeyStroke("D"), "goRight", goRight),
            new Tuple<>(KeyStroke.getKeyStroke("SPACE"), "shoot", shoot)
    };

    public Tuple<Integer, Integer, Integer> getPosition() {
        return position;
    }

    public Tuple<KeyStroke, String, AbstractAction>[] getActions() {
        return actions;
    }

    public Camera getCamera() {
        return camera;
    }
}
