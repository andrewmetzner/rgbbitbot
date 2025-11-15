import java.util.Random;

public class FunctionMaker {
    private int depth;
    private Random rand = new Random();

    public FunctionMaker(int depth) {
        this.depth = depth;
    }

    public PlotFn make() {
        return makeExpr(depth);
    }

    private PlotFn makeExpr(int depth) {
        if (depth == 0) {
            if (rand.nextBoolean()) {
                return new PlotFn.Var(rand.nextBoolean() ? "x" : "y");
            } else {
                return new PlotFn.Literal(rand.nextInt(10) + 1);
            }
        }

        PlotFn arg = makeExpr(depth - 1);

        if (rand.nextDouble() < 0.3) {
            return new PlotFn.Unary(arg);
        } else {
            String[] ops = { "+", "-", "*", "/", "%", "|", "&", "^" };
            PlotFn left = makeExpr(depth - 1);
            PlotFn right = makeExpr(depth - 1);
            String op = ops[rand.nextInt(ops.length)];
            return new PlotFn.Binary(op, left, right);
        }
    }
}
