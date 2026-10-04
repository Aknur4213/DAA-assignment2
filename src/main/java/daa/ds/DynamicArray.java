package daa.ds;

import daa.metrics.Metrics;

public class DynamicArray implements IntList {

    private int[] data;
    private int size;
    private Metrics metrics;

    public DynamicArray(Metrics metrics) {
        data = new int[8];
        size = 0;
        this.metrics = metrics;
    }

    @Override
    public void add(int x) {
        checkCapacity();

        data[size] = x;
        size++;
    }

    @Override
    public void add(int index, int x) {
        checkPositionIndex(index);

        checkCapacity();

        for (int i = size - 1; i >= index; i--) {
            int value = data[i];
            metrics.addStep();

            data[i + 1] = value;
            metrics.addMove();
        }

        data[index] = x;
        size++;
    }

    @Override
    public int remove(int index) {
        checkElementIndex(index);

        int removed = data[index];
        metrics.addStep();

        for (int i = index; i < size - 1; i++) {
            int value = data[i + 1];
            metrics.addStep();

            data[i] = value;
            metrics.addMove();
        }

        size--;

        return removed;
    }

    @Override
    public int get(int index) {
        checkElementIndex(index);

        metrics.addStep();

        return data[index];
    }

    @Override
    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            int value = data[i];
            metrics.addStep();

            metrics.addComparison();

            if (value == x) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private void checkCapacity() {
        if (size < data.length) {
            return;
        }

        int[] bigger = new int[data.length * 2];

        for (int i = 0; i < size; i++) {
            int value = data[i];
            metrics.addStep();

            bigger[i] = value;
            metrics.addMove();
        }

        data = bigger;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}