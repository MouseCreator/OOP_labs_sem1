package univ.lab.problem5.beta;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicMarkableReference;

public final class SkipList2<T> {
    private final int MAX_LEVEL;
    Node<T> head;
    Node<T> tail;
    private Comparator<T> comparator = (Comparator.comparingInt(Object::hashCode));
    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    public SkipList2() {
        MAX_LEVEL = 3;
        initNodes();
    }

    private void initNodes() {
        head = new Node<>(true, MAX_LEVEL);
        tail = new Node<>(false, MAX_LEVEL);
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = new AtomicMarkableReference<>(tail, false);
        }
    }
    public SkipList2(int maxLevel) {
        MAX_LEVEL = maxLevel;
        initNodes();
    }
    public static final class Node<T> {
        final T value;
        final AtomicMarkableReference<Node<T>>[] next;
        private final int topLevel;
        private boolean MIN_NODE = false;
        private boolean MAX_NODE = false;

        // constructor for sentinel nodes
        public Node(boolean min, int maxLevel) {
            if (min) {
                MIN_NODE = true;
            } else {
                MAX_NODE = true;
            }
            value = null;
            next = (AtomicMarkableReference<Node<T>>[])
                    new AtomicMarkableReference[maxLevel + 1];
            for (int i = 0; i < next.length; i++) {
                next[i] = new AtomicMarkableReference<>(null, false);
            }
            topLevel = maxLevel;
        }

        // constructor for ordinary nodes
        public Node(T x, int height) {
            value = x;
            next = (AtomicMarkableReference<Node<T>>[])
                    new AtomicMarkableReference[height + 1];
            for (int i = 0; i < next.length; i++) {
                next[i] = new AtomicMarkableReference<>(null, false);
            }
            topLevel = height;
        }
    }

    public boolean add(T x) {
        int topLevel = randomLevel();
        int bottomLevel = 0;
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        while (true) {
            boolean found = find(x, preds, succs);
            if (found) {
                return false;
            } else {
                Node<T> newNode = new Node<>(x, topLevel);
                for (int level = bottomLevel; level <= topLevel; level++) {
                    Node<T> succ = succs[level];
                    newNode.next[level].set(succ, false);
                }
                Node<T> pred = preds[bottomLevel];
                Node<T> succ = succs[bottomLevel];
                newNode.next[bottomLevel].set(succ, false);
                if (!pred.next[bottomLevel].compareAndSet(succ, newNode,
                        false, false)) {
                    continue;
                }
                for (int level = bottomLevel + 1; level <= topLevel; level++) {
                    while (true) {
                        pred = preds[level];
                        succ = succs[level];
                        if (pred.next[level].compareAndSet(succ, newNode, false, false))
                            break;
                        find(x, preds, succs);
                    }
                }
                return true;
            }
        }
    }
    private final Random r = new Random();
    private int randomLevel() {
        int level = 0;
        for (int i = 0; i < MAX_LEVEL; i++) {
            if (r.nextBoolean())
                break;
            level++;
        }
        return level;
    }

    public boolean remove(T x) {
        int bottomLevel = 0;
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T> succ;
        boolean found = find(x, preds, succs);
        if (!found) {
            return false;
        } else {
            Node<T> nodeToRemove = succs[bottomLevel];
            for (int level = nodeToRemove.topLevel;
                 level >= bottomLevel + 1; level--) {
                boolean[] marked = {false};
                succ = nodeToRemove.next[level].get(marked);
                while (!marked[0]) {
                    nodeToRemove.next[level].attemptMark(succ, true);
                    succ = nodeToRemove.next[level].get(marked);
                }
            }
            boolean[] marked = {false};
            succ = nodeToRemove.next[bottomLevel].get(marked);
            while (true) {
                boolean iMarkedIt =
                        nodeToRemove.next[bottomLevel].compareAndSet(succ, succ,
                                false, true);
                succ = succs[bottomLevel].next[bottomLevel].get(marked);
                if (iMarkedIt) {
                    find(x, preds, succs);
                    return true;
                } else if (marked[0]) return false;
            }
        }
    }

    boolean find(T x, Node<T>[] preds, Node<T>[] succs) {
        int bottomLevel = 0;
        boolean[] marked = {false};
        boolean snip;
        Node<T> pred, succ, curr = null;
        boolean retryNeeded;

        do {
            retryNeeded = false;
            pred = head;
            for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
                curr = pred.next[level].getReference();
                while (true) {
                    succ = curr.next[level].get(marked);
                    while (marked[0]) {
                        snip = pred.next[level].compareAndSet(curr, succ, false, false);
                        if (!snip) {
                            retryNeeded = true;
                            break;
                        }
                        curr = pred.next[level].getReference();
                        succ = curr.next[level].get(marked);
                    }

                    if (retryNeeded) {
                        break;
                    }

                    if (less(curr, x)) {
                        pred = curr;
                        curr = succ;
                    } else {
                        break;
                    }
                }

                if (retryNeeded) {
                    break;
                }

                preds[level] = pred;
                succs[level] = curr;
            }
        } while (retryNeeded);

        return vEquals(curr, x);
    }
    private boolean vEquals(Node<T> t1, T t2) {
        if (t1 == null)
            return false;
        if (t1.MIN_NODE || t1.MAX_NODE)
            return false;
        return comparator.compare(t1.value, t2) == 0;
    }

    private boolean less(Node<T> t1, T t2) {
        if (t1.MIN_NODE)
            return true;
        if (t1.MAX_NODE)
            return false;
        return comparator.compare(t1.value, t2) < 0;
    }

    public boolean contains(T x) {
        int bottomLevel = 0;
        boolean[] marked = {false};
        Node<T> pred = head, curr = null, succ;
        for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
            curr = pred.next[level].getReference();
            while (true) {
                succ = curr.next[level].get(marked);
                while (marked[0]) {
                    curr = pred.next[level].getReference();
                    succ = curr.next[level].get(marked);
                }
                if (less(curr, x)) {
                    pred = curr;
                    curr = succ;
                } else {
                    break;
                }
            }
        }
        return vEquals(curr, x);
    }

    public String print() {
        StringBuilder builder = new StringBuilder();
        StringBuilder general = new StringBuilder();
        int count = 0;
        int bottomLevel = 0;
        boolean[] marked = {false};
        Node<T> pred = head, curr, succ;
        Node<T> initial = pred;
        for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
            curr = initial.next[level].getReference();
            while (true) {
                builder.append("-->").append(curr.value);
                succ = curr.next[level].get(marked);
                while (marked[0]) {
                    curr = pred.next[level].getReference();
                    succ = curr.next[level].get(marked);
                }
                if (!curr.MAX_NODE){
                    pred = curr;
                    curr = succ;
                    count++;
                } else {
                    builder.append("\n");
                    general.append("[").append(count).append("]").append(builder);
                    count = 0;
                    builder = new StringBuilder();
                    break;
                }
            }
        }
        return general.toString();
    }
}