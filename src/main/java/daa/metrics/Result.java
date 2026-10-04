package daa.metrics;

public class Result {

    private String workload;
    private String variant;
    private String structure;
    private int n;

    private double timeMs;

    private long steps;
    private long moves;
    private long comparisons;

    public Result(
            String workload,
            String variant,
            String structure,
            int n,
            double timeMs,
            long steps,
            long moves,
            long comparisons
    ) {
        this.workload = workload;
        this.variant = variant;
        this.structure = structure;
        this.n = n;
        this.timeMs = timeMs;
        this.steps = steps;
        this.moves = moves;
        this.comparisons = comparisons;
    }

    public String getWorkload() {
        return workload;
    }

    public String getVariant() {
        return variant;
    }

    public String getStructure() {
        return structure;
    }

    public int getN() {
        return n;
    }

    public double getTimeMs() {
        return timeMs;
    }

    public long getSteps() {
        return steps;
    }

    public long getMoves() {
        return moves;
    }

    public long getComparisons() {
        return comparisons;
    }
}