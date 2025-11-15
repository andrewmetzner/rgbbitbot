import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Random;
import java.util.function.BiFunction;

public class ComputeContext {

    public static final int EXTENT = 512;
    public static final int MAX_ZOOM = 3;

    private int depth;
    private int attempts;
    private boolean rejectBad;
    private int scale;
    private int extent;

    private Random rand = new Random();

    public ComputeContext(int depth, int attempts, boolean rejectBad, int scalePower) {
        this.depth = depth;
        this.attempts = attempts;
        this.rejectBad = rejectBad;
        this.scale = 1 << scalePower;
        this.extent = EXTENT / this.scale;

        if (scalePower > MAX_ZOOM) throw new RuntimeException("Scale too high!");
    }

    public BufferedImage computeAndRender(Function2D fn) {
        Grid pixels = new Grid(extent, extent);
        pixels.map(fn::apply);

        return render(pixels);
    }

    private BufferedImage render(Grid pixels) {
        BufferedImage image =
	    new BufferedImage(extent * scale, extent * scale, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < pixels.getWidth(); x++) {
            for (int y = 0; y < pixels.getHeight(); y++) {
                double val = pixels.get(x, y);

                int rgb = (int) (Math.min(1.0, Math.max(0.0, val)) * 255);
                Color color = new Color(rgb, rgb, rgb);

                for (int dx = 0; dx < scale; dx++) {
                    for (int dy = 0; dy < scale; dy++) {
                        image.setRGB(x * scale + dx, y * scale + dy, color.getRGB());
                    }
                }
            }
        }

        return image;
    }

    @FunctionalInterface
    public interface Function2D {
        double apply(int x, int y);
    }
}
