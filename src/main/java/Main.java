import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int segLength = 50;

    public static void main(String[] args) throws Exception {
        print("-".repeat(segLength) + "\n");

        System.out.println("Beginning training:");

        DataPoint[] dataPoints = readFileForTraining("MNIST_CSV/mnist_train.csv");
        ANN ann = new ANN(28 * 28, 10, 2, 128, 2);
        final int numEpochs = 20;
        final double[] accuracyHistory = new double[numEpochs];

        DataPoint[] testDataPoints = readFileForTraining("MNIST_CSV/mnist_test.csv");
        for (int i = 0; i < numEpochs; i++) {
            ann.train(dataPoints, 2, 10);
            accuracyHistory[i] = getAccuracy(testDataPoints, ann);
            print("Accuracy after Epoch " + (i + 1) + " = " + (100 * accuracyHistory[i]) + " %\n");
        }

        print("-".repeat(segLength));
    }

    public static void saveModel(ANN model) {

    }

    public static DataPoint[] readFileForTraining(String filename) throws IOException {
        // transform the files containing the information from csv format into my Datapoint format
        int rowLength = 28 * 28 + 1;
        List<String[]> valuesAsString = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                valuesAsString.add(line.split(","));
            }
        }

        List<double[]> valuesAsNum = new ArrayList<>();
        for (String[] sample : valuesAsString) {
            double[] sampleAsNum = new double[sample.length];
            for (int i = 0; i < sample.length; i++) {
                if (i > 0)
                    sampleAsNum[i] = Double.parseDouble(sample[i]) / 255;
                else
                    sampleAsNum[i] = Double.parseDouble(sample[i]);
            }
            valuesAsNum.add(sampleAsNum);
        }

        // extract data
        DataPoint[] dataPoints = new DataPoint[valuesAsNum.size()];

        for (int i = 0; i < valuesAsNum.size(); i++) {
            double[] data = new double[valuesAsNum.getFirst().length - 1];
            System.arraycopy(valuesAsNum.get(i), 1, data, 0, data.length);
            DataPoint dp = new DataPoint(data, oneHotEncoding((int) valuesAsNum.get(i)[0]));
            dataPoints[i] = dp;
        }
        return dataPoints;
    }

    public static double getAccuracy(DataPoint[] testDataPoints, ANN model) {
        int numOfCorrectPredictions = 0;
        for (DataPoint sample : testDataPoints) {
            int prediction = model.predict(sample);
            if (prediction == getIndexOfMaxNumber(sample.label))
                numOfCorrectPredictions++;
        }
        return (double) numOfCorrectPredictions / (double) testDataPoints.length;
    }

    public static int getIndexOfMaxNumber(double[] array) {
        int maxPos = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > array[maxPos])
                maxPos = i;
        }
        return maxPos;
    }

    public static double[] oneHotEncoding(int label) {
        double[] labels = new double[10];

        Arrays.fill(labels, 0);

        labels[label] = 1;
        return labels;
    }

    public static void print(String text) {
        System.out.print(text);
    }
}
