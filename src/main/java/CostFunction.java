public interface CostFunction {
    public double getCost(double expectedValue, double outputValue);

    public double getDerivative(double expectedValue, double outputValue);
}
