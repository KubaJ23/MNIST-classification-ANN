//import java.util.Arrays;
//import java.util.Random;
//
//class CustomVector implements Vector {
//    private final float[] values;
//
//    public CustomVector(float[] values) {
//        this.values = values;
//    }
//
//    public CustomVector(int length) {
//        values = new float[length];
//        randomiseValues();
//    }
//
//    @Override
//    public int getDimension() {
//        return values.length;
//    }
//
//    @Override
//    public void randomiseValues() {
//        Random rand = new Random();
//        for (int i = 0; i < getDimension(); i++) {
//            setValue(i, rand.nextFloat());
//        }
//    }
//
//    @Override
//    public Vector addVector(Vector vector) {
//        if (vector == null)
//            return null;
//        if (vector.getDimension() != getDimension())
//            return null;
//
//        Vector sumVector = new CustomVector(new float[getDimension()]);
//        for (int i = 0; i < getDimension(); i++) {
//            sumVector.setValue(i, vector.getValue(i) + getValue(i));
//        }
//
//        return sumVector;
//    }
//
//    @Override
//    public float getValue(int pos) {
//        return values[pos];
//    }
//
//    @Override
//    public void setValue(int pos, float val) {
//        values[pos] = val;
//    }
//
//    @Override
//    public String toString() {
//        return "vector = " + Arrays.toString(values);
//    }
//
//
//}
