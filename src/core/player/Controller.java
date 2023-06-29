package core.player;

import core.Tuple;
import core.World;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controller {

    private final World world;
    private final Camera camera = new Camera();
    private final Tuple<Double, Double, Double> position = new Tuple<>(50d, 10d, 50d);
    private final Action goForward = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {
            double rX = camera.getRotationX();

            double newX = position.getFirst() + Math.cos(Math.toRadians(rX));
            double newZ = position.getThird() + -Math.sin(Math.toRadians(rX));

            if (!world.playerCollidesBorder((int) newX, (int) position.getSecond().doubleValue(), (int) newX)) {
                position.setFirst(newX);
                position.setThird(newZ);
            }
        }
    };

    private final Action goBack = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {
            double rX = camera.getRotationX();

            double newX = position.getFirst() + -Math.cos(Math.toRadians(rX));
            double newZ = position.getThird() + Math.sin(Math.toRadians(rX));

            if (!world.playerCollidesBorder((int) newX, (int) position.getSecond().doubleValue(), (int) newX)) {
                position.setFirst(newX);
                position.setThird(newZ);
            }
        }
    };

    private final Action goLeft = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {
            double rX = camera.getRotationX();

            double newX = position.getFirst() + Math.sin(Math.toRadians(rX));
            double newZ = position.getThird() + Math.cos(Math.toRadians(rX));

            if (!world.playerCollidesBorder((int) newX, (int) position.getSecond().doubleValue(), (int) newX)) {
                position.setFirst(newX);
                position.setThird(newZ);
            }
        }
    };

    private final Action goRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent event) {
            double rX = camera.getRotationX();

            double newX = position.getFirst() - Math.sin(Math.toRadians(rX));
            double newZ = position.getThird() - Math.cos(Math.toRadians(rX));

            if (!world.playerCollidesBorder((int) newX, (int) position.getSecond().doubleValue(), (int) newX)) {
                position.setFirst(newX);
                position.setThird(newZ);
            }
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

    public Controller(World world) {
        this.world = world;
    }

    public Tuple<Double, Double, Double> getPosition() {
        return position;
    }

    public Tuple<KeyStroke, String, AbstractAction>[] getActions() {
        return actions;
    }

    public Camera getCamera() {
        return camera;
    }
}
