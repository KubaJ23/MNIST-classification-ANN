public class sigmoid implements ActivationFunction {
    @Override
    public double apply(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    @Override
    public double derivative(double x) {
        return apply(x) * (1 - apply(x));
    }
}
