package core.player;

import graphics.UiCore;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Camera implements MouseMotionListener {

    private Robot robot;
    private double rotationX = 90;
    private double rotationY = 90;

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        rotate(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        rotate(mouseEvent);
    }

    private void rotate(MouseEvent mouseEvent) {
        int centerX = UiCore.frame.getX() + UiCore.frame.getWidth() / 2;
        int centerY = UiCore.frame.getY() + UiCore.frame.getHeight() / 2;

        double moveX = (mouseEvent.getX() - centerX) / 30d;
        double moveY = (mouseEvent.getY() - centerY) / 10d;

        rotationX += moveX;
        //rotationY -= moveY;

        if (rotationY < 20) {
            rotationY = 20;
        } else if (rotationY > 140) {
            rotationY = 140;
        }

        centerMouse(centerX, centerY);
    }

    private void centerMouse(int centerX, int centerY) {
            robot.mouseMove(centerX, centerY);
    }

    public double getRotationX() {
        return rotationX;
    }

    public double getRotationY() {
        return rotationY;
    }

    {
        try {
            robot = new Robot();
        } catch (AWTException exception) {
            throw new RuntimeException(exception);
        }
    }
}
