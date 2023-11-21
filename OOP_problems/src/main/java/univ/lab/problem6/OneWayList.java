package univ.lab.problem6;

import java.util.concurrent.atomic.AtomicReference;

public class OneWayList<T> {
        private static class Node<T> {
            final T value;
            Node<T> next;
            Node(T value) {
                this.value = value;
                this.next = null;
            }
        }
        private final AtomicReference<Node<T>> head;
        private final AtomicReference<Node<T>> tail;
        public OneWayList() {
            head = new AtomicReference<>(null);
            tail = new AtomicReference<>(null);
        }

        public void enqueue(T item) {
            Node<T> newNode = new Node<>(item);
            while (true) {
                if (head.get() == null && tail.get() == null) {
                    if (!tail.compareAndSet(null, newNode)) {
                        continue;
                    }
                    head.compareAndSet(null, newNode);
                    break;
                }
                Node<T> tailT = tail.get();
                if (tailT != null) {
                    if (tail.compareAndSet(tailT, newNode)) {
                        tailT.next = newNode;
                    } else {
                        continue;
                    }
                    head.compareAndSet(null, newNode);
                    break;
                }
            }
        }

        public T dequeue() {
            T element;
            while (true) {
                if (head.get()==null) {
                    return null;
                }
                Node<T> headT = head.get();
                if (headT != null) {
                    if (!head.compareAndSet(headT, headT.next)) {
                        continue;
                    }

                    headT.next = null;
                    element = headT.value;
                    break;
                }
            }
            return element;
        }
}
