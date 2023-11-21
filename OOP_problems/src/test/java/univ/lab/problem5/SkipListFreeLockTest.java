package univ.lab.problem5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkipListFreeLockTest {

    private SkipListFreeLock<Integer> skipListFreeLock;

    @BeforeEach
    void setUp() {

    }
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
        skipListFreeLock = new SkipListFreeLock<>(Integer::compareTo, 4);
        skipListFreeLock.add(4);
        skipListFreeLock.add(3);
        skipListFreeLock.add(2);
        skipListFreeLock.add(1);

        assertTrue(skipListFreeLock.contains(4));
        assertTrue(skipListFreeLock.contains(3));
        assertTrue(skipListFreeLock.contains(2));
        assertTrue(skipListFreeLock.contains(1));


        System.out.println(skipListFreeLock.print(4));
    }

    @Test
    void remove() {
    }

    @Test
    void contains() {
    }
}