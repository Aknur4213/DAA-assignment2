package daa.ds;

import daa.metrics.Metrics;

public class MyLinkedList implements IntList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private Metrics metrics;

    public MyLinkedList(Metrics metrics) {
        this.metrics = metrics;
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
            tail = newNode;

            metrics.addMove();
            metrics.addMove();
        } else {
            tail.next = newNode;
            metrics.addMove();

            tail = newNode;
            metrics.addMove();
        }

        size++;
    }

    @Override
    public void add(int index, int x) {
        checkPositionIndex(index);

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            metrics.addMove();

            head = newNode;
            metrics.addMove();

            if (size == 0) {
                tail = newNode;
                metrics.addMove();
            }

            size++;
            return;
        }

        Node previous = getNode(index - 1);

        newNode.next = previous.next;
        metrics.addMove();

        previous.next = newNode;
        metrics.addMove();

        if (index == size) {
            tail = newNode;
            metrics.addMove();
        }

        size++;
    }

    @Override
    public int remove(int index) {
        checkElementIndex(index);

        if (index == 0) {
            int removed = head.value;

            head = head.next;
            metrics.addStep();
            metrics.addMove();

            size--;

            if (size == 0) {
                tail = null;
                metrics.addMove();
            }

            return removed;
        }

        Node previous = getNode(index - 1);

        Node removedNode = previous.next;
        metrics.addStep();

        previous.next = removedNode.next;
        metrics.addStep();
        metrics.addMove();

        if (removedNode == tail) {
            tail = previous;
            metrics.addMove();
        }

        size--;

        return removedNode.value;
    }

    @Override
    public int get(int index) {
        checkElementIndex(index);

        Node node = getNode(index);

        return node.value;
    }

    @Override
    public boolean contains(int x) {
        Node current = head;

        while (current != null) {
            metrics.addComparison();

            if (current.value == x) {
                return true;
            }

            current = current.next;
            metrics.addStep();
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private Node getNode(int index) {
        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.addStep();
        }

        return current;
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