package daa;

import daa.ds.DynamicArray;
import daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    void addAndGetWorks() {
        DynamicArray array = new DynamicArray(new Metrics());

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void insertAtIndexWorks() {
        DynamicArray array = new DynamicArray(new Metrics());

        array.add(10);
        array.add(20);
        array.add(30);

        array.add(1, 99);

        assertEquals(10, array.get(0));
        assertEquals(99, array.get(1));
        assertEquals(20, array.get(2));
        assertEquals(30, array.get(3));
    }

    @Test
    void removeWorks() {
        DynamicArray array = new DynamicArray(new Metrics());

        array.add(10);
        array.add(20);
        array.add(30);

        int removed = array.remove(1);

        assertEquals(20, removed);
        assertEquals(2, array.size());
        assertEquals(30, array.get(1));
    }

    @Test
    void containsWorks() {
        DynamicArray array = new DynamicArray(new Metrics());

        array.add(5);
        array.add(10);
        array.add(15);

        assertTrue(array.contains(10));
        assertFalse(array.contains(99));
    }

    @Test
    void duplicatesWork() {
        DynamicArray array = new DynamicArray(new Metrics());

        array.add(7);
        array.add(7);

        assertTrue(array.contains(7));
        assertEquals(2, array.size());
    }

    @Test
    void invalidIndexThrowsException() {
        DynamicArray array = new DynamicArray(new Metrics());

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(0)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(0)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.add(-1, 5)
        );
    }
}