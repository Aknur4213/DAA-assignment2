package daa;

import daa.ds.MyLinkedList;
import daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void addAndGetWorks() {
        MyLinkedList list = new MyLinkedList(new Metrics());

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void insertAtIndexWorks() {
        MyLinkedList list = new MyLinkedList(new Metrics());

        list.add(10);
        list.add(20);
        list.add(30);

        list.add(1, 99);

        assertEquals(10, list.get(0));
        assertEquals(99, list.get(1));
        assertEquals(20, list.get(2));
        assertEquals(30, list.get(3));
    }

    @Test
    void removeWorks() {
        MyLinkedList list = new MyLinkedList(new Metrics());

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(30, list.get(1));
    }

    @Test
    void containsWorks() {
        MyLinkedList list = new MyLinkedList(new Metrics());

        list.add(5);
        list.add(10);
        list.add(15);

        assertTrue(list.contains(10));
        assertFalse(list.contains(99));
    }

    @Test
    void duplicatesWork() {
        MyLinkedList list = new MyLinkedList(new Metrics());

        list.add(7);
        list.add(7);

        assertTrue(list.contains(7));
        assertEquals(2, list.size());
    }

    @Test
    void firstAndLastIndexWork() {
        MyLinkedList list = new MyLinkedList(new Metrics());

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(30, list.get(list.size() - 1));
    }

    @Test
    void invalidIndexThrowsException() {
        MyLinkedList list = new MyLinkedList(new Metrics());

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(0)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(0)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(-1, 5)
        );
    }
}