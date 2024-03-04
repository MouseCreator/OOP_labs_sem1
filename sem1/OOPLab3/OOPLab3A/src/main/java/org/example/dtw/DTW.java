package org.example.dtw;

import java.util.List;

public interface DTW<T> {
    double dtwDistance(T[] seq1, T[] seq2);
    double dtwNormalizedDistance(T[] seq1, T[] seq2);
    double dtwNormalizedDistance(List<T> seq1, List<T> seq2);
}
