package univ.lab.problem5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {

    @Test
    void add() {
        SkipList<Integer> skipList = new SkipList<>(Integer::compareTo, 4);
        List<Integer> list = createList(40);
        for (int p : list) {
            skipList.add(p);
        }
        System.out.println(skipList.print());
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
    void remove() {
        SkipList<Integer> skipList = new SkipList<>(Integer::compareTo, 4);
        List<Integer> list = createList(50);
        for (int p : list) {
            skipList.add(p);
        }
        Collections.shuffle(list);
        List<Integer> sublist = list.subList(0, 10);
        for (int p : sublist) {
            assertTrue(skipList.remove(p));
        }
        for (int p : sublist) {
            assertFalse(skipList.contains(p));
        }
        assertTrue(skipList.testStructure());
    }

    @Test
    void contains() {
    }
}