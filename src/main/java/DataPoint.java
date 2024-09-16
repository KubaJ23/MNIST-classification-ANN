public class DataPoint {
    // what the values for input nodes will be
    double[] data;

    // what are the expected values for the output nodes
    double[] label;

    public DataPoint(double[] data, double[] label) {
        this.data = data;
        this.label = label;
    }
}
