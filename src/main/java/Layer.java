import java.util.Arrays;
import java.util.Random;

public class Layer {
    final private double[][] weights;
    final private double[] bias;

    final private double[][] weightGradients;
    final private double[] biasGradients;

    final private double[] output;
    final private double[] weightedValues;

    ActivationFunction activationFunction;

    public Layer(int numNodesIn, int numNodesOut, ActivationFunction func, CostFunction costFunc, int randomParametersSeed) {
        weights = new double[numNodesOut][numNodesIn];
        bias = new double[numNodesOut];

        randomiseParameters(randomParametersSeed);

        output = new double[weights.length];
        weightedValues = new double[weights.length];

        weightGradients = new double[numNodesOut][numNodesIn];
        biasGradients = new double[numNodesOut];
        // sets both gradients arrays to all 0s - 'resets' ready to accumulate next batch of 'changes'
        resetGradients();

        this.activationFunction = func;
    }

    public void calculateOutput(double[] input) {
        for (int i = 0; i < weights.length; i++) {
            double sum = bias[i];
            for (int j = 0; j < weights[0].length; j++) {
                sum += input[j] * weights[i][j];
            }
            weightedValues[i] = sum;
            output[i] = activationFunction.apply(sum);
        }
    }

    public double[] calculateNodeValuesForLastLayer(DataPoint datapoint, CostFunction costFunction) {
        // node values array will determine by how much the cost is
        // changed when the given node's weighted value is changed
        double[] nodeValues = new double[output.length];

        for (int i = 0; i < nodeValues.length; i++) {
            nodeValues[i] = activationFunction.derivative(weightedValues[i]) * costFunction.getDerivative(datapoint.label[i], output[i]);
        }
        return nodeValues;
    }

    public double[] calculateNodeValuesForHiddenLayer(Layer nextLayer, double[] oldNodeValues) {
        final double[] nodeValues = new double[output.length];
        Arrays.fill(nodeValues, 0);
        final double[][] nextLayerWeights = nextLayer.getWeights();

        for (int i = 0; i < nodeValues.length; i++) {
            // now iterate over all the weights connecting a node i from current layer to node j in next layer
            for (int j = 0; j < oldNodeValues.length; j++) {
                nodeValues[i] += oldNodeValues[j] * nextLayerWeights[j][i];
            }
            nodeValues[i] *= activationFunction.derivative(weightedValues[i]);
        }
        return nodeValues;
    }

    public void addToAllGradients(double[] inputs, double[] nodeValues) {
        addToWeightGradients(inputs, nodeValues);
        addToBiasGradients(nodeValues);
    }

    public void addToWeightGradients(double[] inputs, double[] nodeValues) {
        for (int i = 0; i < weightGradients.length; i++) {
            for (int j = 0; j < weightGradients[0].length; j++) {
                weightGradients[i][j] += inputs[j] * nodeValues[i];
            }
        }
    }

    public void addToBiasGradients(double[] nodeValues) {
        for (int i = 0; i < biasGradients.length; i++) {
            biasGradients[i] += nodeValues[i];
        }
    }

    public void applyGradients(double learningRate, int batchSize) {
        double learningRateTimeAverage = (learningRate / batchSize);
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] -= weightGradients[i][j] * learningRateTimeAverage;
            }
        }
        for (int i = 0; i < bias.length; i++) {
            bias[i] -= biasGradients[i] * learningRateTimeAverage;
        }
    }

    public void resetGradients() {
        for (int i = 0; i < weightGradients.length; i++) {
            for (int j = 0; j < weightGradients[0].length; j++) {
                weightGradients[i][j] = 0;
            }
        }

        Arrays.fill(biasGradients, 0);
    }

    public double[] getOutput() {
        return output;
    }

    public double[][] getWeights() {
        return weights;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    private void randomiseParameters(int seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < weights.length; i++) {
            bias[i] = rand.nextDouble() * 2 - 1;
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = rand.nextDouble() * 2 - 1;
            }
        }
    }
}
