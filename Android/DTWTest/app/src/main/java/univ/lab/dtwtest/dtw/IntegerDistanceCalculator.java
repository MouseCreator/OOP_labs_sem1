package univ.lab.dtwtest.dtw;

public class IntegerDistanceCalculator implements DistanceCalculator<Integer> {
    @Override
    public double calculate(Integer i1, Integer i2) {
        return Math.abs(i1 - i2);
    }
}
