package univ.lab.problem5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SkipList<T> {
    private final List<List<Node<T>>> layers;
    private final Random random;
    private double insertProbability = 0.5;
    private final Comparator<T> comparator;
    private final int layerNum;
    public SkipList(Comparator<T> comparator, int layerNum) {
        this.layerNum = layerNum;
        this.comparator = comparator;
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
        return find(element) != null;
    }
    public static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> levelDown;
    }
    private Node<T> find(T element) {
        Node<T> current = topLevel();
        if (current == null) {
            return null;
        }
        for (int i = 0; i < layerNum; i++) {
            current = moveForward(current, element);
            if (current.element.equals(element)) {
                return current;
            }
            current = current.levelDown;
        }
        return null;
    }

    private Node<T> moveForward(Node<T> current, T element) {
        while (true) {
            if (current.next == null) {
                return current;
            }
            if (comparator.compare(element, current.next.element) < 0) {
                current = current.next;
                continue;
            }
            return current;
        }
    }

    private Node<T> topLevel() {
        for (int i = layerNum - 1; i > -1; i--) {
            if (layers.get(i).isEmpty())
                continue;
            return layers.get(i).get(0);
        }
        return null;
    }

    public double getInsertProbability() {
        return insertProbability;
    }

    public void setInsertProbability(double insertProbability) {
        this.insertProbability = insertProbability;
    }
}
