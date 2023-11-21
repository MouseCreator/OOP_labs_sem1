package univ.lab.problem5;


import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class SkipListFreeLock<T> {
    private final int maxHeight;
    private final Comparator<T> comparator;
    private final AtomicNode<T> head;
    private final Random random = new Random();
    public SkipListFreeLock(Comparator<T> comparator, int maxHeight) {
        this.maxHeight = maxHeight;
        this.comparator = comparator;
        head = AtomicNode.head(maxHeight);
        AtomicNode<T> tail = AtomicNode.tail(maxHeight);
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = new AtomicMarkableReference<>(tail, false);
        }
    }

    private static class AtomicNode<T> {
        public boolean isHead;
        public boolean isTail;
        private final T value;
        private final int topLevel;
        private final AtomicMarkableReference<AtomicNode<T>>[] next;
        private AtomicNode(int maxHeight) {
            value = null;
            next = initNext(maxHeight);
            for (int i = 0; i < next.length; i++) {
                next[i] = new AtomicMarkableReference<>(null, false);
            }
            topLevel = maxHeight;
        }

        private AtomicNode(T value, int height) {
            this.value = value;
            next = initNext(height);
            for (int i = 0; i < next.length; i++) {
                next[i] = new AtomicMarkableReference<>(null, false);
            }
            topLevel = height;
        }

        private AtomicMarkableReference<AtomicNode<T>>[] initNext(int height) {
            final AtomicMarkableReference<AtomicNode<T>>[] next;
            next = new AtomicMarkableReference[height +1];
            return next;
        }

        public static <T> AtomicNode<T> head(int maxHeight) {
            AtomicNode<T> node = new AtomicNode<>(maxHeight);
            node.isHead = true;
            return node;
        }
        public static <T> AtomicNode<T> tail(int maxHeight) {
            AtomicNode<T> node =  new AtomicNode<>(maxHeight);
            node.isTail = true;
            return node;
        }

        public static <T> AtomicNode<T> newNode(T value, int height) {
            return new AtomicNode<>(value, height);
        }
    }

    public synchronized String print(T value) {
        StringBuilder builder = new StringBuilder();
        int bottomLevel = 0;
        boolean[] marked = { false };
        AtomicNode<T> predecessor = head;
        AtomicNode<T> current = null;
        AtomicNode<T> successor;
        for (int i = maxHeight; i >= bottomLevel; i--) {
            current = predecessor.next[i].getReference();
            builder.append("-->").append(current.value);
            while (true) {
                successor = current.next[i].get(marked);
                while (marked[0]) {
                    current = predecessor.next[i].getReference();
                    successor = current.next[i].get(marked);
                }
                if (compare(current, value) < 0) {
                    predecessor = current;
                    current = successor;
                } else {
                    break;
                }
            }
        }
        return builder.toString();
    }


    public boolean add(T value) {
        int topLevel = chooseRandomLevel();
        int bottomLevel = 0;
        AtomicNode<T>[] predecessors = relativeCollection();
        AtomicNode<T>[] successors = relativeCollection();
        while (true) {
            boolean found = find(value, predecessors, successors);
            if (found) {
                return false;
            }
            AtomicNode<T> newNode = AtomicNode.newNode(value, topLevel);
            for (int i = bottomLevel; i <= topLevel; i++) {
                AtomicNode<T> successor = successors[i];
                newNode.next[i].set(successor, false);
            }
            AtomicNode<T> predecessor = predecessors[bottomLevel];
            AtomicNode<T> successor = successors[bottomLevel];
            newNode.next[bottomLevel].set(successor, false);
            if (!predecessor.next[bottomLevel].compareAndSet(successor, newNode, false, false))
                continue;
            for (int i = bottomLevel + 1; i <= topLevel; i++) {
                while (true) {
                    predecessor = predecessors[i];
                    successor = successors[i];
                    if (predecessor.next[i].compareAndSet(successor, newNode, false, false))
                        break;
                    find(value, predecessors, successors);
                }
            }
            return true;
        }
    }

    public boolean remove(T value) {
        int bottomLevel = 0;
        AtomicNode<T>[] predecessors = relativeCollection();
        AtomicNode<T>[] successors = relativeCollection();
        AtomicNode<T> successor;
        boolean found = find(value, predecessors, successors);
        if (!found) {
            return false;
        }
        AtomicNode<T> nodeToRemove = successors[bottomLevel];
        for (int i = nodeToRemove.topLevel; i >= bottomLevel+1; i--) {
            boolean[] marked = { false };
            successor = nodeToRemove.next[i].get(marked);
            while (!marked[0]) {
                nodeToRemove.next[i].attemptMark(successor, true);
                successor = nodeToRemove.next[i].get(marked);
            }
        }
        boolean[] marked = { false };
        successor = nodeToRemove.next[bottomLevel].get(marked);
        while (true) {
            boolean markedIt = nodeToRemove.next[bottomLevel].compareAndSet(successor, successor, false, true);
            successor = successors[bottomLevel].next[bottomLevel].get(marked);
            if (markedIt) {
                find(value, predecessors, successors);
                return true;
            }
            else if (marked[0]) {
                return false;
            }
        }
    }
    private static class ReferenceContainer<T> {
        private boolean r;
        private AtomicNode<T> curr;

        public ReferenceContainer() {
            r = false;
            curr = null;
        }
    }
    private boolean find(T value, AtomicNode<T>[] predecessors, AtomicNode<T>[] successors) {
        boolean[] marked = {false};
        boolean r = true;
        ReferenceContainer<T> container = new ReferenceContainer<>();
        while (r) {
            r = onFind(value, predecessors, successors, marked, container);
        }
        return container.r;
    }

    private boolean onFind(T value, AtomicNode<T>[] predecessors, AtomicNode<T>[] successors,
                           boolean[] marked, ReferenceContainer<T> cr) {
        AtomicNode<T> successor;
        boolean snip;
        AtomicNode<T> predecessor;
        int bottomLevel = 0;
        /* repeat loop */
        predecessor = head;
        for (int i = maxHeight; i >= bottomLevel; i--) {
            cr.curr = predecessor.next[i].getReference();
            while (true) {
                successor = cr.curr.next[i].get(marked);
                while (marked[0]) {
                    snip = predecessor.next[i].compareAndSet(cr.curr, successor, false, false);
                    if (!snip) {
                        return true;
                    }
                    cr.curr = predecessor.next[i].getReference();
                    successor = cr.curr.next[i].get(marked);
                }
                if (compare(cr.curr, value) < 0) {
                    predecessor = cr.curr;
                    cr.curr = successor;
                } else {
                    break;
                }
            }
            predecessors[i] = predecessor;
            successors[i] = cr.curr;
        }
        cr.r = (compare(cr.curr, value) == 0);
        return false;

    }

    public boolean contains(T value) {
        int bottomLevel = 0;
        boolean[] marked = { false };
        AtomicNode<T> predecessor = head;
        AtomicNode<T> current = null;
        AtomicNode<T> successor;
        for (int i = maxHeight; i >= bottomLevel; i--) {
            current = predecessor.next[i].getReference();
            while (true) {
                successor = current.next[i].get(marked);
                while (marked[0]) {
                    current = predecessor.next[i].getReference();
                    successor = current.next[i].get(marked);
                }
                if (compare(current, value) < 0) {
                    predecessor = current;
                    current = successor;
                } else {
                    break;
                }
            }
        }
        if (current == null)
            return false;
        return compare(current, value) == 0;
    }

    private int compare(AtomicNode<T> current, T value) {
        if (current.isHead) {
            return -1;
        }
        if (current.isTail) {
            return 1;
        }
        return comparator.compare(current.value, value);
    }

    private AtomicNode<T>[] relativeCollection() {
        return new AtomicNode[maxHeight + 1];
    }

    private int chooseRandomLevel() {
        return random.nextInt(maxHeight+1);
    }

}
