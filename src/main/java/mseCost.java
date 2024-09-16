public class mseCost implements CostFunction {

    @Override
    public double getCost(double expectedValue, double outputValue) {
        return Math.pow(outputValue - expectedValue, 2);
    }

    @Override
    public double getDerivative(double expectedValue, double outputValue) {
        return 2 * (outputValue - expectedValue);
    }
}
