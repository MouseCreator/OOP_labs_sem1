package univ.lab.problem5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkipList<T> {
    private final List<List<T>> layers;
    private final Random random;
    private double insertProbability = 0.5;
    public SkipList(int layerNum) {
        this.layers = new ArrayList<>(layerNum);
        for (int i = 0; i < layerNum; i++) {
            layers.add(new ArrayList<>());
        }
        random = new Random();
    }

    private boolean isInsert() {
        return random.nextDouble() < insertProbability;
    }

    public void add(T element) {

    }
    public boolean remove(T element) {
        return false;
    }
    public boolean contains(T element) {
        return false;
    }

    public double getInsertProbability() {
        return insertProbability;
    }

    public void setInsertProbability(double insertProbability) {
        this.insertProbability = insertProbability;
    }
}
