package univ.lab.problem5.beta;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkipList2Test {

    private static List<Integer> createList(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    private static List<Integer> createList(int init, int n, int step) {
        List<Integer> list = new ArrayList<>();
        for (int i = init, j = 0; j < n; j++, i += step) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }

    @Test
    void add() {
        for (int iter = 0; iter < 5; iter++) {
            SkipList<Integer> skipList = new SkipList<>();
            skipList.setComparator(Integer::compareTo);
            int N = 500;
            int M = 3;
            Thread writer = new Thread(() -> {
                List<Integer> even = createList(0, N, M);
                for (int i = 0; i < N; i++) {
                    skipList.add(even.get(i));
                }
            });
            Thread writer2 = new Thread(() -> {
                List<Integer> odd = createList(1, N, M);
                for (int i = 0; i < N; i++) {
                    skipList.add(odd.get(i));
                }
            });
            Thread writer3 = new Thread(() -> {
                List<Integer> odd = createList(2, N, M);
                for (int i = 0; i < N; i++) {
                    skipList.add(odd.get(i));
                }
            });
            writer.start();
            writer2.start();
            writer3.start();

            try {
                writer.join();
                writer2.join();
                writer3.join();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < N * M; i++) {
                assertTrue(skipList.contains(i), "Skip list does not contain value: " + i);
            }
            assertEquals(N*M, skipList.rawSize());
            System.out.println(skipList.print() + "\n\n");
        }
    }

    @Test
    void remove() {
        for (int iter = 0; iter < 5; iter++) {
            SkipList<Integer> skipList = new SkipList<>();
            skipList.setComparator(Integer::compareTo);
            int N = 500;
            int M = 2;
            for (int i = 0; i < M * N; i++) {
                skipList.add(i);
            }
            Thread writer = new Thread(() -> {
                List<Integer> even = createList(0, N, M);
                for (int i = 0; i < N; i++) {
                    skipList.remove(even.get(i));
                }
            });
            Thread writer2 = new Thread(() -> {
                List<Integer> odd = createList(1, N, M);
                for (int i = 0; i < N; i++) {
                    skipList.remove(odd.get(i));
                }
            });

            writer.start();
            writer2.start();

            try {
                writer.join();
                writer2.join();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertEquals(0, skipList.rawSize());
            System.out.println(skipList.print() + "\n\n");
        }
    }
}