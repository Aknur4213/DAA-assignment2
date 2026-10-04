package daa.ds;

import daa.metrics.Metrics;

public class MinHeap {

    private int[] heap;
    private int size;
    private Metrics metrics;

    public MinHeap(Metrics metrics) {
        heap = new int[8];
        size = 0;
        this.metrics = metrics;
    }

    public void insert(int x) {
        checkCapacity();

        heap[size] = x;

        int current = size;
        size++;

        while (current > 0) {
            int parent = (current - 1) / 2;

            int currentValue = heap[current];
            int parentValue = heap[parent];

            metrics.addStep();
            metrics.addStep();

            metrics.addComparison();

            if (parentValue <= currentValue) {
                break;
            }

            heap[current] = parentValue;
            heap[parent] = currentValue;

            metrics.addMove();
            metrics.addMove();

            current = parent;
        }
    }

    public int peekMin() {
        checkNotEmpty();

        metrics.addStep();

        return heap[0];
    }

    public int extractMin() {
        checkNotEmpty();

        int min = heap[0];
        metrics.addStep();

        size--;

        if (size == 0) {
            return min;
        }

        int last = heap[size];
        metrics.addStep();

        heap[0] = last;
        metrics.addMove();

        bubbleDown();

        return min;
    }

    public int size() {
        return size;
    }

    private void bubbleDown() {
        int current = 0;

        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;

            if (left >= size) {
                break;
            }

            int smallest = left;

            if (right < size) {
                int leftValue = heap[left];
                int rightValue = heap[right];

                metrics.addStep();
                metrics.addStep();

                metrics.addComparison();

                if (rightValue < leftValue) {
                    smallest = right;
                }
            }

            int currentValue = heap[current];
            int childValue = heap[smallest];

            metrics.addStep();
            metrics.addStep();

            metrics.addComparison();

            if (currentValue <= childValue) {
                break;
            }

            heap[current] = childValue;
            heap[smallest] = currentValue;

            metrics.addMove();
            metrics.addMove();

            current = smallest;
        }
    }

    public boolean hasValidHeapProperty() {
        for (int child = 1; child < size; child++) {
            int parent = (child - 1) / 2;

            if (heap[parent] > heap[child]) {
                return false;
            }
        }

        return true;
    }

    private void checkCapacity() {
        if (size < heap.length) {
            return;
        }

        int[] bigger = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {
            int value = heap[i];
            metrics.addStep();

            bigger[i] = value;
            metrics.addMove();
        }

        heap = bigger;
    }

    private void checkNotEmpty() {
        if (size == 0) {
            throw new IllegalStateException();
        }
    }
}