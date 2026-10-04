package daa;

import daa.ds.MinHeap;
import daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void insertAndPeekWork() {
        MinHeap heap = new MinHeap(new Metrics());

        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(3);

        assertEquals(3, heap.peekMin());
        assertEquals(4, heap.size());
    }

    @Test
    void extractMinWorks() {
        MinHeap heap = new MinHeap(new Metrics());

        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(3);

        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.peekMin());
        assertEquals(3, heap.size());
    }

    @Test
    void duplicatesWork() {
        MinHeap heap = new MinHeap(new Metrics());

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void extractMinReturnsSortedOrder() {
        MinHeap heap = new MinHeap(new Metrics());

        int[] values = {
                8, 3, 10, 1, 7, 3, 20, -5
        };

        for (int value : values) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {

            int current = heap.extractMin();

            assertTrue(current >= previous);

            previous = current;
        }
    }

    @Test
    void oneElementWorks() {
        MinHeap heap = new MinHeap(new Metrics());

        heap.insert(42);

        assertEquals(42, heap.peekMin());
        assertEquals(42, heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    void emptyHeapThrowsException() {
        MinHeap heap = new MinHeap(new Metrics());

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }
    @Test
    void heapPropertyAlwaysHolds() {
        MinHeap heap = new MinHeap(new Metrics());

        int[] values = {
                10, 4, 15, 2, 8, 20, 1, 7, 3
        };

        for (int value : values) {
            heap.insert(value);

            assertTrue(heap.hasValidHeapProperty());
        }

        while (heap.size() > 0) {
            heap.extractMin();

            assertTrue(heap.hasValidHeapProperty());
        }
    }
}