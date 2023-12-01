package univ.lab.problem5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SkipListFreeLockTest {
    private static List<Integer> createList(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }
    @Test
    void add() {
        SkipListFreeLock<Integer> lockFreeSkipList = new SkipListFreeLock<>(Integer::compareTo);
        lockFreeSkipList.setSeed(5L);
        lockFreeSkipList.add(1);
        lockFreeSkipList.add(2);
        lockFreeSkipList.add(3);
        lockFreeSkipList.add(4);
        lockFreeSkipList.add(5);
        lockFreeSkipList.add(6);
        lockFreeSkipList.add(7);
        assertTrue(lockFreeSkipList.contains(4));
        assertTrue(lockFreeSkipList.contains(3));
        assertTrue(lockFreeSkipList.contains(2));
        assertTrue(lockFreeSkipList.contains(1));
        String exp = """
                -->3-->4-->7-->null
                -->3-->4-->7-->null
                -->1-->2-->3-->4-->7-->null
                -->1-->2-->3-->4-->5-->7-->null
                -->1-->2-->3-->4-->5-->6-->7-->null
                """;
        String act = lockFreeSkipList.print();
        System.out.println(act);
        assertEquals(exp, act);
    }

    @Test
    void delete() {
        List<Integer> list = createList(50);
        SkipListFreeLock<Integer> lockFreeSkipList = new SkipListFreeLock<>(Integer::compareTo);
        for (int p : list) {
            lockFreeSkipList.add(p);
        }
        Collections.shuffle(list);
        List<Integer> removed = list.subList(0, 10);
        for (int p : removed) {
            assertTrue(lockFreeSkipList.delete(p));
        }
        for (int p : removed) {
            assertFalse(lockFreeSkipList.contains(p));
        }
    }

    @Test
    void contains() {
        SkipListFreeLock<Integer> skipList = new SkipListFreeLock<>(Integer::compareTo);
        List<Integer> list = createList(40);
        for (int p : list) {
            skipList.add(p);
        }
        list = list.stream().sorted().toList();
        for (int p : list) {
            assertTrue(skipList.contains(p), "List does not contain " + p);
        }
        int a = list.stream().max(Integer::compareTo).orElse(0)+1;
        assertFalse(skipList.contains(a));
    }

    @Test
    void multiThreadTest() {
        List<Integer> list = createList(100);
        SkipListFreeLock<Integer> skipList = new SkipListFreeLock<>(Integer::compareTo);
        for (int p : list) {
            skipList.add(p);
        }
        Random random = new Random();
        int N = 200;
        Thread writer = new Thread(()->{
            for (int i = 0; i < N; i++) {
                skipList.add(random.nextInt(100));
            }
        });
        Thread listener = new Thread(()->{
            for (int i = 0; i < N; i++) {
                skipList.contains(random.nextInt(100));
            }
        });
        Thread remover = new Thread(()->{
            for (int i = 0; i < N/2; i++) {
                skipList.delete(random.nextInt(100));
            }
        });
        writer.start();
        listener.start();
        remover.start();

        try {
            writer.join();
            listener.join();
            remover.join();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        //System.out.println(skipList.print());
    }

    @Test
    void evenOdd() {
        SkipListFreeLock<Integer> skipList = new SkipListFreeLock<>(Integer::compareTo);
        Random random = new Random();
        int N = 200;
        Thread writer = new Thread(()->{
            for (int i = 0; i < N; i++) {
                skipList.add(random.nextInt(100) * 2);
            }
        });
        Thread writer2 = new Thread(()->{
            for (int i = 0; i < N; i++) {
                skipList.add(random.nextInt(100) * 2 + 1);
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
        System.out.println(skipList.print());
    }
}