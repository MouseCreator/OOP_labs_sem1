package univ.lab.problem5;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicMarkableReference;

public final class SkipListFreeLock<T> {
    private static final int MAX_LEVEL = 4;
    private final Node<T> head = new Node<>(false);
    private final Comparator<T> comparator;
    private final Random random = new Random();
    public SkipListFreeLock(Comparator<T> comparator) {
        this.comparator = comparator;
        for (int i = 0; i < head.next.length; i++) {
            Node<T> tail = new Node<>(true);
            head.next[i]
                    = new AtomicMarkableReference<>(tail, false);
        }
    }

    public static final class Node<T> {
        final T value;
        final AtomicMarkableReference<Node<T>>[] next;
        private final int topLevel;
        private boolean isHead = false;
        private boolean isTail = false;
        public Node(boolean tail) {
            value = null;
            if (tail) {
                isTail = true;
            } else {
                isHead = true;
            }
            next = getNext(MAX_LEVEL);
            for (int i = 0; i < next.length; i++) {
                next[i] = new AtomicMarkableReference<>(null, false);
            }
            topLevel = MAX_LEVEL;
        }
        public Node(T value, int height) {
            this.value = value;
            next = getNext(height);
            for (int i = 0; i < next.length; i++) {
                next[i] = new AtomicMarkableReference<>(null, false);
            }
            topLevel = height;
        }

        private static <T> AtomicMarkableReference<Node<T>>[] getNext(int height) {
            return new AtomicMarkableReference[height + 1];
        }
    }
    private static <T> Node<T>[] collectionNode() {
        return new Node[MAX_LEVEL + 1];
    }
    public boolean add(T value) {
        int topLevel = randomLevel();
        int bottomLevel = 0;
        Node<T>[] predecessors = collectionNode();
        Node<T>[] successors = collectionNode();
        while (true) {
            boolean found = find(value, predecessors, successors);
            if (found) {
                return false;
            } else {
                Node<T> newNode = new Node<>(value, topLevel);
                for (int i = bottomLevel; i <= topLevel; i++) {
                    Node<T> successor = successors[i];
                    newNode.next[i].set(successor, false);
                }
                Node<T> predecessor = predecessors[bottomLevel];
                Node<T> successor = successors[bottomLevel];
                newNode.next[bottomLevel].set(successor, false);
                if (!predecessor.next[bottomLevel].compareAndSet(successor, newNode,
                        false, false)) {
                    continue;
                }
                for (int level = bottomLevel+1; level <= topLevel; level++) {
                    while (true) {
                        predecessor = predecessors[level];
                        successor = successors[level];
                        if (predecessor.next[level].compareAndSet(successor, newNode, false, false))
                            break;
                        find(value, predecessors, successors);
                    }
                }
                return true;
            }
        }
    }

    private int randomLevel() {
        return random.nextInt(MAX_LEVEL+1);
    }

    public boolean delete(T value) {
        int bottomLevel = 0;
        Node<T>[] predecessors = collectionNode();
        Node<T>[] successors = collectionNode();
        Node<T> successor;
        boolean found = find(value, predecessors, successors);
        if (!found) {
            return false;
        } else {
            Node<T> nodeToRemove = successors[bottomLevel];
            for (int i = nodeToRemove.topLevel; i >= bottomLevel + 1; i--) {
                boolean[] marked = {false};
                successor = nodeToRemove.next[i].get(marked);
                while (!marked[0]) {
                    nodeToRemove.next[i].attemptMark(successor, true);
                    successor = nodeToRemove.next[i].get(marked);
                }
            }
            boolean[] marked = {false};
            successor = nodeToRemove.next[bottomLevel].get(marked);
            while (true) {
                boolean markedNode = nodeToRemove.next[bottomLevel].compareAndSet(successor, successor,false, true);
                successor = successors[bottomLevel].next[bottomLevel].get(marked);
                if (markedNode) {
                    find(value, predecessors, successors);
                    return true;
                } else if (marked[0]) return false;
            }
        }
    }

    public boolean find(T x, Node<T>[] prevN, Node<T>[] nextV) {
        int bottomLevel = 0;
        boolean[] marked = {false};
        boolean snip;
        Node<T> prev, current = null, next;
        retry:
        while (true) {
            prev = head;
            for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
                current = prev.next[level].getReference();
                while (true) {
                    next = current.next[level].get(marked);
                    while (marked[0]) {
                        snip = prev.next[level].compareAndSet(current, next,
                                false, false);
                        if (!snip) continue retry;
                        current = prev.next[level].getReference();
                        next = current.next[level].get(marked);
                    }
                    if (compare(current, x) < 0) {
                        prev = current; current = next;
                    } else {
                        break;
                    }
                }
                prevN[level] = prev;
                nextV[level] = current;
            }
            return compare(current, x) == 0;
        }
    }

    private int compare(Node<T> node, T value) {
        if (node.isHead) {
            return -1;
        }
        if (node.isTail) {
            return 1;
        }
        return comparator.compare(node.value, value);
    }

    boolean contains(T x) {
        int bottomLevel = 0;
        boolean[] marked = {false};
        Node<T> prev = head;
        Node<T> curr = null;
        Node<T> next;
        for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
            curr = prev.next[level].getReference();
            while (true) {
                next = curr.next[level].get(marked);
                while (marked[0]) {
                    curr = prev.next[level].getReference();
                    next = curr.next[level].get(marked);
                }
                if (compare(curr, x) < 0) {
                    prev = curr;
                    curr = next;
                } else {
                    break;
                }
            }
        }
        return (compare(curr, x) == 0);
    }

    public String print() {
        StringBuilder builder = new StringBuilder();
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
                if (!curr.isTail){
                    pred = curr;
                    curr = succ;
                } else {
                    builder.append("\n");
                    break;
                }
            }
        }
        return builder.toString();
    }

    public void setSeed(long seed) {
        this.random.setSeed(seed);
    }

}
