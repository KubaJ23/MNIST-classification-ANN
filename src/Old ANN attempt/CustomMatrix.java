import java.util.Arrays;
import java.util.Random;

public class CustomMatrix implements Matrix {
    private final double[][] values;

    public CustomMatrix(int inputDimension, int outputDimension) {
        values = new double[outputDimension][inputDimension];
        randomiseValues();
    }

    public CustomMatrix(double[][] setValues) {
        values = setValues;
    }

    @Override
    public double getValue(int row, int col) {
        return values[row][col];
    }

    @Override
    public void setValue(int row, int col, double val) {
        values[row][col] = val;
    }

    @Override
    public int getInputDimension() {
        return values[0].length;
    }

    @Override
    public int getOutputDimension() {
        return values.length;
    }

    @Override
    public void randomiseValues() {
        Random rand = new Random();
        for (int i = 0; i < getOutputDimension(); i++) {
            for (int j = 0; j < getInputDimension(); j++) {
                setValue(i, j, rand.nextFloat());
            }
        }
    }

    @Override
    public Matrix transform(Matrix inputMatrix) {
        if (inputMatrix == null)
            return null;
        if (inputMatrix.getOutputDimension() != getInputDimension())
            return null;

        double[][] output = new double[getOutputDimension()][inputMatrix.getInputDimension()];

        for (int k = 0; k < inputMatrix.getInputDimension(); k++) {
            for (int i = 0; i < getOutputDimension(); i++) {
                double sum = 0;
                for (int j = 0; j < getInputDimension(); j++) {
                    sum += getValue(i, j) * inputMatrix.getValue(j, k);
                }
                output[i][k] = sum;
            }
        }
        return new CustomMatrix(output);
    }

    @Override
    public Matrix applyActivationFunction(ActivationFunction func) {
        for (int i = 0; i < getOutputDimension(); i++) {
            for (int j = 0; j < getInputDimension(); j++) {
                values[i][j] = func.apply(values[i][j]);
            }
        }
        return this;
    }

    @Override
    public Matrix add(Matrix matrix) throws Exception {
        if (matrix.getInputDimension() != getInputDimension() || matrix.getOutputDimension() != getOutputDimension())
            throw new Exception();

        for (int i = 0; i < getOutputDimension(); i++) {
            for (int j = 0; j < getInputDimension(); j++) {
                this.setValue(i, j, matrix.getValue(i, j) + this.getValue(i, j));
            }
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Values = ");
        builder.append("[");
        for (int i = 0; i < getOutputDimension(); i++) {
            builder.append("[");
            for (int j = 0; j < getInputDimension(); j++) {
                builder.append(getValue(i, j));
                if (j + 1 < getInputDimension())
                    builder.append(", ");
            }
            builder.append("]");
            if (i + 1 < getOutputDimension())
                builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }
}
