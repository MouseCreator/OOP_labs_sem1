package univ.lab.dtwtest.dtw;
@FunctionalInterface
public interface DistanceCalculator<T> {
    double calculate(T t, T t1);
}
