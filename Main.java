import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) throws IOException {
        FunctionMaker fm = new FunctionMaker(5);

        PlotFn fx = fm.make();
        PlotFn fy = fm.make();
        PlotFn fz = fm.make();

        ComputeContext cc = new ComputeContext(4, 20, false, 1);

        int extent = 512;
        BufferedImage img = new BufferedImage(extent, extent, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < extent; x++) {
            for (int y = 0; y < extent; y++) {
                double val1 = fx.eval(x, y);
                double val2 = fy.eval(x, y);
                double val3 = fz.eval(x, y);

                int r = (int)(Math.abs(val1) % 256);
                int g = (int)(Math.abs(val2) % 256);
                int b = (int)(Math.abs(val3) % 256);

                int rgb = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, rgb);
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);

        String baseName = "procedural_" + timestamp;
        String imgFilename = baseName + ".png";
        String txtFilename = baseName + ".txt";

	// Make sure media directory exists
	File mediaDir = new File("media");
	if (!mediaDir.exists()) {
	    mediaDir.mkdirs();
	}

	File imgFile = new File(mediaDir, imgFilename);
	ImageIO.write(img, "PNG", imgFile);

	File txtFile = new File(mediaDir, txtFilename);
	try (FileWriter writer = new FileWriter(txtFile)) {
	    writer.write("f(x,y) red:   " + fx.toString() + "\n");
	    writer.write("f(x,y) green: " + fy.toString() + "\n");
	    writer.write("f(x,y) blue:  " + fz.toString() + "\n");
	}

	System.out.println("Generated procedural image: " + imgFile.getAbsolutePath());
	System.out.println("Function definitions saved to: " + txtFile.getAbsolutePath());


    }
}
