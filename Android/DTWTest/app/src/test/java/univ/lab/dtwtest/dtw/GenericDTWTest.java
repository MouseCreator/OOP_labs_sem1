package univ.lab.dtwtest.dtw;

import junit.framework.TestCase;

import org.junit.Test;

public class GenericDTWTest extends TestCase {
    @Test
    public void testDtwDistance() {
        GenericDTW<Integer> dtw = new GenericDTW<>((a, b) -> Math.abs(a-b));
        Integer[] a = {1, 7, 4, 8, 2, 9, 6, 5, 2, 0};
        Integer[] b = {1, 2, 8, 5, 5, 1, 9, 4, 6, 5};
        assertEquals(17.0, dtw.dtwDistance(a, b));
    }

    @Test
    public void testIdentityDistance() {
        GenericDTW<Integer> dtw = new GenericDTW<>((a, b) -> Math.abs(a-b));
        Integer[] a = {1, 7, 4, 8, 2, 9, 6, 5, 2, 0};
        Integer[] b = {1, 7, 4, 8, 2, 9, 6, 5, 2, 0};
        assertEquals(0.0, dtw.dtwDistance(a, b));
    }
}