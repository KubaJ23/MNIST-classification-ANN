public class ANN {
    private final Matrix[] layers;

    private final Matrix[] biases;
    private final Matrix[] weights;
    ActivationFunction activationFunction;

    private final int inputLayerHeight;
    private final int hiddenLayerHeight;
    private final int outputLayerHeight;

    /*
    matrix of weights and bias:
        ________________
        | w1 | w2 | w3 |
        |----+----+----+
        | w1 | w2 | w3 |
        |----+----+----+
        | w1 | w2 | w3 |
        |----+----+----+
        | w1 | w2 | w3 |
        |_______________

     vector/layer:

        ( val1  )
        ( val2  )
        ( val3  )
     */

    public ANN(int inputLayerHeight, int hiddenLayerHeight, int outputLayerHeight, int numHiddenLayers) {
        this.inputLayerHeight = inputLayerHeight;
        this.hiddenLayerHeight = hiddenLayerHeight;
        this.outputLayerHeight = outputLayerHeight;

        // define activation function
        activationFunction = new sigmoid();

        //create the layers and weights arrays
        layers = new Matrix[numHiddenLayers + 2];
        biases = new Matrix[numHiddenLayers + 1];
        weights = new Matrix[numHiddenLayers + 1];

        // set weights for input layer
        weights[0] = new CustomMatrix(inputLayerHeight, hiddenLayerHeight);

        // set weights and bias for output layer
        weights[weights.length - 1] = new CustomMatrix(hiddenLayerHeight, outputLayerHeight);

        //set weights and bias for between all hidden layers
        for (int i = 1; i < weights.length - 1; i++) {
            weights[i] = new CustomMatrix(hiddenLayerHeight, hiddenLayerHeight);
        }

        for (int i = 0; i < biases.length - 1; i++) {
            biases[i] = new CustomMatrix(1, hiddenLayerHeight);
        }
        biases[biases.length - 1] = new CustomMatrix(1, outputLayerHeight);
    }

    private boolean validInput(Matrix input) {
        return input != null && input.getOutputDimension() == inputLayerHeight && input.getInputDimension() == 1;
    }

    public void propogate(Matrix input) throws Exception {
        if (!validInput(input))
            throw new Exception();

        this.layers[0] = input;

        for (int i = 0; i < layers.length - 1; i++) {
            layers[i + 1] = weights[i].transform(layers[i]).applyActivationFunction(activationFunction).add(biases[i]);
        }
    }

    public Matrix[] getLayers() {
        return layers;
    }

    public void Train(double[][] data, double[] labels) {
        //split data and labels
    }
}
