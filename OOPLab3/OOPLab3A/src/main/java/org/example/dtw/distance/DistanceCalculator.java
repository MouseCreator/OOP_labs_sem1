package org.example.dtw.distance;
@FunctionalInterface
public interface DistanceCalculator<T> {
    double calculate(T t, T t1);
}
