package graphics;

import core.Player;
import core.Tuple;
import core.World;

import javax.swing.*;
import java.awt.*;

/**
 * The UI core class responsible for rendering the game window and handling user input.
 */
public class UiCore extends JPanel {
    private static long renderTime = 0;
    private static Frame frame;

    private final World world;
    private final Player player;
    private final RenderingHints renderingHints;

    /**
     * Constructs a new UiCore instance with the specified world and player.
     *
     * @param world  The game world.
     * @param player The player object.
     */
    public UiCore(World world, Player player) {
        this.world = world;
        this.player = player;
        this.renderingHints = createRenderingHints();
        setupInputMapping();
        frame = new Frame(UiConstants.title, this);
        frame.setInvisibleCursor();
    }

    /**
     * Sets up the input mapping for key bindings.
     */
    private void setupInputMapping() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        Tuple<KeyStroke, String, AbstractAction>[] actions = player.getController().getActions();

        for (Tuple<KeyStroke, String, AbstractAction> action : actions) {
            inputMap.put(action.getFirst(), action.getSecond());
            actionMap.put(action.getSecond(), action.getThird());
        }

        addMouseMotionListener(player.getController().getCamera());
    }

    /**
     * Creates rendering hints for graphics rendering.
     *
     * @return The rendering hints.
     */
    private RenderingHints createRenderingHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        hints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        return hints;
    }

    /**
     * Creates a Render3D object for rendering the game world.
     *
     * @param graphics2D The graphics context used for rendering.
     * @return The Render3D object.
     */
    private Render3D createRender3D(Graphics2D graphics2D) {
        return new Render3D(
                world.getDimension(),
                graphics2D,
                getWidth(),
                getHeight(),
                25,
                5,
                25,
                player.getController().getCamera().getRotationX(),
                player.getController().getCamera().getRotationY()
        );
    }

    /**
     * Opens the game window and makes it visible.
     */
    public void openWindow() {
        frame.setVisible(true);
    }

    /**
     * Renders the game world onto the panel's graphics context.
     *
     * @param graphics The graphics context used for rendering.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHints(renderingHints);

        Render3D render3D = createRender3D(graphics2D);

        renderTime = System.nanoTime();

        try {
            render3D.render();
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }

        System.out.println("Rendered: " + ((System.nanoTime() - renderTime) / 1_000_000) + "ms");

        renderTime = 0;
    }

    public static Frame getFrame() {
        return frame;
    }
}
