package daa.bench;

import daa.ds.DynamicArray;
import daa.ds.IntList;
import daa.ds.MinHeap;
import daa.ds.MyLinkedList;
import daa.metrics.CsvWriter;
import daa.metrics.Metrics;
import daa.metrics.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Benchmark {

    private static final int WARMUP_RUNS = 1;
    private static final int MEASURED_RUNS = 5;

    private static final int RANDOM_ACCESS_CALLS = 10_000;
    private static final int SEARCH_CALLS = 1_000;
    private static final int INSERT_REMOVE_CALLS = 1_000;

    private static final int[] SIZES =
            geometricProgression(100, 10, 4);

    public static void main(String[] args) {

        List<Result> results = new ArrayList<>();

        for (int n : SIZES) {

            int[] data = generateData(n);

            results.add(runW1("DynamicArray", n, data));
            results.add(runW1("MyLinkedList", n, data));

            results.add(runW2("DynamicArray", n, data));
            results.add(runW2("MyLinkedList", n, data));

            results.add(runW3("DynamicArray", "head", n, data));
            results.add(runW3("MyLinkedList", "head", n, data));

            results.add(runW3("DynamicArray", "middle", n, data));
            results.add(runW3("MyLinkedList", "middle", n, data));

            results.add(runW4(n, data));
        }

        CsvWriter.write(
                "results/results.csv",
                results
        );

        System.out.println("Benchmark finished.");
        System.out.println("Results saved to results/results.csv");
    }

    // W1 random access

    private static Result runW1(
            String structureName,
            int n,
            int[] data
    ) {

        int[] indexes =
                generateRandomIndexes(n, RANDOM_ACCESS_CALLS);

        Trial[] trials = new Trial[MEASURED_RUNS];

        int measured = 0;

        for (int run = 0;
             run < WARMUP_RUNS + MEASURED_RUNS;
             run++) {

            Metrics metrics = new Metrics();

            IntList list =
                    createList(structureName, data, metrics);

            metrics.reset();

            long start = System.nanoTime();

            long checksum = 0;

            for (int index : indexes) {
                checksum += list.get(index);
            }

            long end = System.nanoTime();

            if (checksum == Long.MIN_VALUE) {
                System.out.println(checksum);
            }

            if (run >= WARMUP_RUNS) {

                double timeMs =
                        (end - start) / 1_000_000.0;

                trials[measured] =
                        new Trial(
                                timeMs,
                                metrics.getSteps(),
                                metrics.getMoves(),
                                metrics.getComparisons()
                        );

                measured++;
            }
        }

        Trial median = findMedian(trials);

        return new Result(
                "W1_random_access",
                "-",
                structureName,
                n,
                median.timeMs,
                median.steps,
                median.moves,
                median.comparisons
        );
    }

    // W2 search

    private static Result runW2(
            String structureName,
            int n,
            int[] data
    ) {

        int[] queries = generateSearchQueries(data);

        Trial[] trials = new Trial[MEASURED_RUNS];

        int measured = 0;

        for (int run = 0;
             run < WARMUP_RUNS + MEASURED_RUNS;
             run++) {

            Metrics metrics = new Metrics();

            IntList list =
                    createList(structureName, data, metrics);

            metrics.reset();

            long start = System.nanoTime();

            int found = 0;

            for (int query : queries) {

                if (list.contains(query)) {
                    found++;
                }
            }

            long end = System.nanoTime();

            if (found < 0) {
                System.out.println(found);
            }

            if (run >= WARMUP_RUNS) {

                double timeMs =
                        (end - start) / 1_000_000.0;

                trials[measured] =
                        new Trial(
                                timeMs,
                                metrics.getSteps(),
                                metrics.getMoves(),
                                metrics.getComparisons()
                        );

                measured++;
            }
        }

        Trial median = findMedian(trials);

        return new Result(
                "W2_search",
                "-",
                structureName,
                n,
                median.timeMs,
                median.steps,
                median.moves,
                median.comparisons
        );
    }

    // W3 insert and remove

    private static Result runW3(
            String structureName,
            String variant,
            int n,
            int[] data
    ) {

        Trial[] trials = new Trial[MEASURED_RUNS];

        int measured = 0;

        for (int run = 0;
             run < WARMUP_RUNS + MEASURED_RUNS;
             run++) {

            Metrics metrics = new Metrics();

            IntList list =
                    createList(structureName, data, metrics);

            metrics.reset();

            long start = System.nanoTime();

            if (variant.equals("head")) {

                for (int i = 0;
                     i < INSERT_REMOVE_CALLS;
                     i++) {

                    list.add(0, i);
                }

                for (int i = 0;
                     i < INSERT_REMOVE_CALLS;
                     i++) {

                    list.remove(0);
                }

            } else {

                for (int i = 0;
                     i < INSERT_REMOVE_CALLS;
                     i++) {

                    int middle = list.size() / 2;

                    list.add(middle, i);
                }

                for (int i = 0;
                     i < INSERT_REMOVE_CALLS;
                     i++) {

                    int middle = list.size() / 2;

                    list.remove(middle);
                }
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {

                double timeMs =
                        (end - start) / 1_000_000.0;

                trials[measured] =
                        new Trial(
                                timeMs,
                                metrics.getSteps(),
                                metrics.getMoves(),
                                metrics.getComparisons()
                        );

                measured++;
            }
        }

        Trial median = findMedian(trials);

        return new Result(
                "W3_insert_remove",
                variant,
                structureName,
                n,
                median.timeMs,
                median.steps,
                median.moves,
                median.comparisons
        );
    }

    // W4 - priority processing

    private static Result runW4(
            int n,
            int[] data
    ) {

        Trial[] trials = new Trial[MEASURED_RUNS];

        int measured = 0;

        for (int run = 0;
             run < WARMUP_RUNS + MEASURED_RUNS;
             run++) {

            Metrics metrics = new Metrics();

            MinHeap heap = new MinHeap(metrics);

            long start = System.nanoTime();

            for (int value : data) {
                heap.insert(value);
            }

            int previous = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {

                int current = heap.extractMin();

                if (current < previous) {
                    throw new IllegalStateException(
                            "Heap output is not sorted"
                    );
                }

                previous = current;
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {

                double timeMs =
                        (end - start) / 1_000_000.0;

                trials[measured] =
                        new Trial(
                                timeMs,
                                metrics.getSteps(),
                                metrics.getMoves(),
                                metrics.getComparisons()
                        );

                measured++;
            }
        }

        Trial median = findMedian(trials);

        return new Result(
                "W4_priority_processing",
                "-",
                "MinHeap",
                n,
                median.timeMs,
                median.steps,
                median.moves,
                median.comparisons
        );
    }

    // creating array/linked list

    private static IntList createList(
            String structureName,
            int[] data,
            Metrics metrics
    ) {

        IntList list;

        if (structureName.equals("DynamicArray")) {

            list = new DynamicArray(metrics);

        } else {

            list = new MyLinkedList(metrics);
        }

        for (int value : data) {
            list.add(value);
        }

        return list;
    }

    // random data

    private static int[] generateData(int n) {

        Random random = new Random(42);

        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt();
        }

        return data;
    }

    // w1 random indexes

    private static int[] generateRandomIndexes(
            int n,
            int count
    ) {

        Random random = new Random(42);

        int[] indexes = new int[count];

        for (int i = 0; i < count; i++) {

            indexes[i] = random.nextInt(n);
        }

        return indexes;
    }

    // w2 search queries

    private static int[] generateSearchQueries(
            int[] data
    ) {

        int[] queries =
                new int[SEARCH_CALLS];

        Random random = new Random(42);

        // First 500 are definitely present.
        for (int i = 0;
             i < SEARCH_CALLS / 2;
             i++) {

            int randomIndex =
                    random.nextInt(data.length);

            queries[i] =
                    data[randomIndex];
        }

        // Remaining 500 must be absent.
        int position = SEARCH_CALLS / 2;

        int candidate = Integer.MIN_VALUE;

        while (position < SEARCH_CALLS) {

            if (!plainContains(data, candidate)) {

                queries[position] = candidate;

                position++;
            }

            candidate++;
        }

        return queries;
    }

    private static boolean plainContains(
            int[] data,
            int value
    ) {

        for (int x : data) {

            if (x == value) {
                return true;
            }
        }

        return false;
    }

    // geometric progression

    private static int[] geometricProgression(
            int start,
            int ratio,
            int count
    ) {

        int[] values = new int[count];

        int current = start;

        for (int i = 0; i < count; i++) {

            values[i] = current;

            current *= ratio;
        }

        return values;
    }

    // median

    private static Trial findMedian(
            Trial[] trials
    ) {

        for (int i = 1;
             i < trials.length;
             i++) {

            Trial current = trials[i];

            int j = i - 1;

            while (
                    j >= 0 &&
                            trials[j].timeMs > current.timeMs
            ) {

                trials[j + 1] = trials[j];

                j--;
            }

            trials[j + 1] = current;
        }

        return trials[trials.length / 2];
    }

    // one measured run

    private static class Trial {

        double timeMs;

        long steps;
        long moves;
        long comparisons;

        Trial(
                double timeMs,
                long steps,
                long moves,
                long comparisons
        ) {

            this.timeMs = timeMs;
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }
}