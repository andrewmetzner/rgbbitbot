public class Grid {
    private int width, height;
    private double[][] points;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        points = new double[width][height];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public double get(int x, int y) {
        return points[x][y];
    }

    public void set(int x, int y, double value) {
        points[x][y] = value;
    }

    public void map(ComputeContext.Function2D fn) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                points[x][y] = fn.apply(x, y);
            }
        }
    }
}
