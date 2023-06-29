package graphics;

import core.Player;
import core.Tuple;
import core.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * The UI core class responsible for rendering the game window and handling user input.
 */
public class UiCore extends JPanel {

    public final static Frame frame;

    private final World world;
    private final Player player;
    private final RenderingHints renderingHints;
    private final ComponentListener resizeListener;

    private final Render3D render = new Render3D();
    private final Font defaultFont = new Font("Arial", Font.BOLD, 30);

    private long renderTime = 0;

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
        this.addComponentListener(resizeListener);

        setupInputMapping();

        frame.setContentPane(this);
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
     * Updates the data which is getting parsed to the render every tick
     *
     * @param graphics2D The graphics context used for rendering.
     */
    private void updateTickData(Graphics2D graphics2D) {
        render.parseTickData(
                graphics2D,
                player.getController().getPosition().getFirst(),
                player.getController().getPosition().getSecond(),
                player.getController().getPosition().getThird(),
                player.getController().getCamera().getRotationX(),
                player.getController().getCamera().getRotationY()
        );
    }

    /**
     * Updates the data which is getting parsed to the render once a world or screen size update occurred
     */
    public void updateWorldData() {
        render.parseWorldData(
                world.getDimension(),
                getWidth(),
                getHeight()
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

        updateTickData(graphics2D);

        renderTime = System.nanoTime();

        try {
            render.renderFrame();
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }

        drawMap(graphics2D);

        graphics.setColor(Color.WHITE);
        graphics2D.setFont(defaultFont);

        try {
            graphics2D.drawString("FPS: " + (1000 / ((System.nanoTime() - renderTime) / 1_000_000)), 100, 100);
        } catch (ArithmeticException exception) {

        }

        renderTime = 0;
    }

    private void drawMap(Graphics2D graphics2D) {
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawRect(getWidth() - 250, 50, 200, 200);

        graphics2D.setColor(Color.RED);
        graphics2D.drawRect((int) (getWidth() - 250 + (player.getController().getPosition().getFirst() * 2)), (int) (50 + (player.getController().getPosition().getThird() * 2)), 3, 3);
    }

    static {
        frame = new Frame(UiValues.title);
        frame.setInvisibleCursor();
    }

    {
        resizeListener = new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent event) {
                updateWorldData();
            }

            @Override
            public void componentMoved(ComponentEvent event) {

            }

            @Override
            public void componentShown(ComponentEvent event) {

            }

            @Override
            public void componentHidden(ComponentEvent event) {

            }
        };
    }
}
