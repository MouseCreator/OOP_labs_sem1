package univ.lab.ninjagame1.dtw;

public interface DTW<T> {
    double dtwDistance(T[] seq1, T[] seq2);
    double dtwNormalizedDistance(T[] seq1, T[] seq2);
}
