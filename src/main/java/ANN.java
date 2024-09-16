public class ANN {
    Layer[] layers;
    ActivationFunction actFunc = new sigmoid();
    CostFunction costFunc = new mseCost();

    public ANN(int inputSize, int outputSize, int numberOfHiddenLayers, int hiddenLayerSize, int seedForRandomness) {
        layers = new Layer[numberOfHiddenLayers + 1];

        layers[0] = new Layer(inputSize, hiddenLayerSize, actFunc, costFunc, seedForRandomness);
        for (int i = 1; i < layers.length - 1; i++) {
            layers[i] = new Layer(hiddenLayerSize, hiddenLayerSize, actFunc, costFunc, seedForRandomness);
        }
        layers[layers.length - 1] = new Layer(hiddenLayerSize, outputSize, actFunc, costFunc, seedForRandomness);
    }

    public void train(DataPoint[] data, double learnRate, int batchSize) {
        for (int i = 0; i < data.length; i += batchSize) {
            for (int j = i; j < (i + batchSize) && j < data.length; j++) {
                // find and accumulate gradients for weights and biases based on samples
                updateAllGradients(data[j]);
            }
            // apply gradients with learning rate
            for (int k = 0; k < layers.length; k++) {
                layers[k].applyGradients(learnRate, batchSize);
            }

            // reset gradients ready for next batch to be learnt
            resetAllGradients();
        }

    }

    public void updateAllGradients(DataPoint sample) {
        // run sample through network forward
        // this is so that the outputs and weighted values are calculated for this sample
        updateOutputs(sample);

        Layer outputLayer = layers[layers.length - 1];
        double[] nodeValues = outputLayer.calculateNodeValuesForLastLayer(sample, costFunc);
        outputLayer.addToAllGradients(layers[layers.length - 2].getOutput(), nodeValues);

        for (int i = layers.length - 2; i > 0; i--) {
            nodeValues = layers[i].calculateNodeValuesForHiddenLayer(layers[i + 1], nodeValues);
            layers[i].addToAllGradients(layers[i - 1].getOutput(), nodeValues);
        }

        // for first layer, must insert inputs to entire ANN separately
        nodeValues = layers[0].calculateNodeValuesForHiddenLayer(layers[1], nodeValues);
        layers[0].addToAllGradients(sample.data, nodeValues);
    }

    public void resetAllGradients() {
        for (int i = 0; i < layers.length; i++) {
            layers[i].resetGradients();
        }
    }

    private void updateOutputs(DataPoint sample) {
        layers[0].calculateOutput(sample.data);
        for (int i = 1; i < layers.length; i++) {
            layers[i].calculateOutput(layers[i - 1].getOutput());
        }
    }

    public int predict(DataPoint sample) {
        updateOutputs(sample);
        double[] output = layers[layers.length - 1].getOutput();

        //find highest number prediction
        int highestIndex = 0;
        for (int i = 0; i < output.length; i++) {
            if (output[i] > output[highestIndex]) {
                highestIndex = i;
            }
        }
        return highestIndex;
    }
}
