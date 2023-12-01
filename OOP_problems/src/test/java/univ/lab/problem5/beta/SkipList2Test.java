package univ.lab.problem5.beta;

import org.junit.jupiter.api.Test;
import univ.lab.problem5.SkipListFreeLock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
            SkipList2<Integer> skipList = new SkipList2<>();
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
            System.out.println(skipList.print() + "\n\n");
        }
    }
}