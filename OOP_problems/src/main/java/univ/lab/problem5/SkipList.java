package univ.lab.problem5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SkipList<T> {
    private final Node<T> head;
    private final Random random;
    private double insertProbability = 0.5;
    private final Comparator<T> comparator;
    private final int height;
    public SkipList(Comparator<T> comparator, int height) {
        this.comparator = comparator;
        this.head = Node.headNode(height);
        random = new Random();
        this.height = height;
    }

    private boolean coinFlip() {
        return random.nextDouble() < insertProbability;
    }

    public void add(T element) {
        Node<T> tNode = topLevel();
        if (tNode == null) {
            return;
        }
        recursiveInsertion(tNode, element);
    }
    public boolean remove(T element) {
        Node<T> tNode = topLevel();
        if (tNode == null)
            return false;
        return recursiveDeletion(tNode, element);
    }
    public boolean contains(T element) {
        return find(element) != null;
    }

    protected boolean testStructure() {
        List<T> elements = new ArrayList<>();
        Node<T> lowest = lowerLevel();
        while (lowest != null) {
            elements.add(lowest.element);
            lowest = lowest.next;
        }
        Node<T> initial = head;
        while (initial!= null) {
            List<T> currentList = new ArrayList<>(elements);
            Node<T> current = initial.next;
            while (current != null) {
                if (!currentList.remove(current.element)) {
                    return false;
                }
                if (current.prev.next != current) {
                    return false;
                }
                current = current.next;
            }
            initial = initial.levelDown;
        }
        return true;
    }

    private Node<T> lowerLevel() {
        Node<T> current = head;
        while (current.levelDown != null) {
            current = current.levelDown;
        }
        return current;
    }

    public static class Node<T> {
        private final T element;
        private Node<T> next;
        private Node<T> prev;
        private final Node<T> levelDown;
        private boolean head;
        public Node(T element, Node<T> prev, Node<T> next, Node<T> levelDown) {
            this.element = element;
            this.next = next;
            this.prev = prev;
            this.levelDown = levelDown;
            head = false;
        }
        public static <T> Node<T> headNode(int num) {
            Node<T> tNode;
            if (num == 1) {
                tNode = new Node<>(null, null, null, null);
            } else {
                tNode = new Node<>(null, null, null, headNode(num-1));
            }
            tNode.head = true;
            return tNode;
        }
    }
    private boolean recursiveDeletion(Node<T> begin, T element) {
        Node<T> current = moveForward(begin, element);
        boolean deletionSuccess = false;
        if (current.levelDown != null) {
            deletionSuccess = recursiveDeletion(current.levelDown, element);
        }
        if (current.head) {
            return deletionSuccess;
        }
        boolean v = current.element.equals(element);
        if (v) {
            delete(current);
        }
        return current.levelDown == null ? v : deletionSuccess;
    }

    private void delete(Node<T> current) {
        Node<T> next = current.next;
        Node<T> prev = current.prev;
        if (next != null) {
            next.prev = prev;
        }
        if (prev != null) {
            prev.next = next;
        }
    }

    private Node<T> insert(Node<T> prev, T element, Node<T> levelDown) {
        assert prev != null;
        Node<T> newNode = new Node<>(element, prev, prev.next, levelDown);
        Node<T> next = prev.next;
        if (next != null) {
            next.prev = newNode;
        }
        prev.next = newNode;
        return newNode;
    }
    private Node<T> recursiveInsertion(Node<T> begin, T element) {
        Node<T> prev = moveForward(begin, element);
        if (prev.levelDown == null) {
            return insert(prev, element, null);
        }
        Node<T> levelDown = recursiveInsertion(prev.levelDown, element);
        if (levelDown == null) {
            return null;
        }
        return coinFlip() ? insert(prev, element, levelDown) : null;
    }
    private Node<T> find(T element) {
        Node<T> closest = findClosest(element);
        if (closest == null || closest.head) {
            return null;
        }
        return closest.element.equals(element) ? closest : null;
    }

    private Node<T> findClosest(T element) {
        Node<T> node = topLevel();
        if (node == null)
            return null;
        Node<T> current = node;
        for (int i = 1; i < height; i++) {
            current = moveForward(current, element);
            current = current.levelDown;
        }
        return moveForward(current, element);
    }

    private Node<T> moveForward(Node<T> current, T element) {
        while (true) {
            if (current.next == null) {
                return current;
            }
            if (comparator.compare(element, current.next.element) >= 0) {
                current = current.next;
                continue;
            }
            return current;
        }
    }

    private Node<T> topLevel() {
        return head;
    }

    public double getInsertProbability() {
        return insertProbability;
    }

    public void setInsertProbability(double insertProbability) {
        this.insertProbability = insertProbability;
    }

    public String print() {
        StringBuilder builder = new StringBuilder();
        Node<T> initial = topLevel();
        int i = 0;
        while (initial != null) {
            builder.append("[Level ").append(height-i).append("]");
            i++;
            Node<T> current = initial.next;
            while (current != null) {
                builder.append(" --> ").append(current.element);
                current = current.next;
            }
            builder.append("\n");
            initial = initial.levelDown;
        }
        return builder.toString();
    }
}
