package graphics;

import core.Player;
import core.Tuple;
import core.World;

import javax.swing.*;
import java.awt.*;

public class UiCore extends JPanel {

    private final World world;
    private final Player player;

    private final Frame frame;
    private final RenderingHints renderingHints;

    public UiCore(World world, Player player) {
        this.world = world;
        this.player = player;

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        Tuple<KeyStroke, String, AbstractAction>[] actions = player.getController().getActions();

        for (Tuple<KeyStroke, String, AbstractAction> action : actions){
            inputMap.put(action.getFirst(), action.getSecond());
            actionMap.put(action.getSecond(), action.getThird());
        }
    }

    public void openWindow() {
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHints(renderingHints);

        Render3D.render(graphics2D, world.getDimension(), getWidth(), getHeight(), player.getController().getPosition().x, player.getController().getPosition().y, 20, 140, 120);
    }

    {
        frame = new Frame(UiConstants.title, this);
        renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
