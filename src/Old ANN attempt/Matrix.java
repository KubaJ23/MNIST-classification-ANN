import java.awt.*;

public interface Matrix {
    public double getValue(int row, int col);

    public void setValue(int row, int col, double val);

    public int getInputDimension();

    public int getOutputDimension();

    public void randomiseValues();

    public Matrix transform(Matrix inputMatrix);

    public Matrix applyActivationFunction(ActivationFunction func);

    public Matrix add(Matrix matrix) throws Exception;
}
