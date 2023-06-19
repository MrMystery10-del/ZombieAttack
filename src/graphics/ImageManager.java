package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {

    private static BufferedImage invisibleCursor;

    public static BufferedImage getInvisibleCursor() {
        return invisibleCursor;
    }

    static {
        try {
            invisibleCursor = ImageIO.read(ImageManager.class.getResource("/images/InvisibleCursor.png"));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
