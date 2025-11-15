import java.util.Random;

// Base class
abstract class PlotFn {
    public abstract double eval(int x, int y);

    public abstract String toString();

    public static class Var extends PlotFn {
        private String name;
        public Var(String name) { this.name = name; }
        @Override
        public double eval(int x, int y) {
            return "x".equals(name) ? x : y;
        }
        @Override
        public String toString() { return name; }
    }

    public static class Literal extends PlotFn {
        private int value;
        public Literal(int value) { this.value = value; }
        @Override
        public double eval(int x, int y) { return value; }
        @Override
        public String toString() { return Integer.toString(value); }
    }

    public static class Unary extends PlotFn {
        private PlotFn arg;
        public Unary(PlotFn arg) { this.arg = arg; }
        @Override
        public double eval(int x, int y) { return -arg.eval(x, y); }
        @Override
        public String toString() { return "(-" + arg.toString() + ")"; }
    }

    public static class Binary extends PlotFn {
        private String op;
        private PlotFn left, right;
        public Binary(String op, PlotFn left, PlotFn right) {
            this.op = op; this.left = left; this.right = right;
        }

        @Override
        public double eval(int x, int y) {
            double l = left.eval(x, y);
            double r = right.eval(x, y);
            switch(op) {
                case "+": return l + r;
                case "-": return l - r;
                case "*": return l * r;
                case "/": return r != 0 ? l / r : 0;
                case "%": return r != 0 ? l % r : 0;
                case "|": return (int)l | (int)r;
                case "&": return (int)l & (int)r;
                case "^": return (int)l ^ (int)r;
                default: return 0;
            }
        }

        @Override
        public String toString() {
            return "(" + left.toString() + " " + op + " " + right.toString() + ")";
        }
    }
}
