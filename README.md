# DAA Assignment 2 - Data Structures

## Project Description

This project implements three custom data structures from scratch:

- `DynamicArray`
- `MyLinkedList`
- `MinHeap`

The project compares their performance using several workloads and records:

- execution time
- steps
- moves
- comparisons

The benchmark results are saved in:

```text
results/results.csv
```

The generated plots are saved in:

```text
results/plots/
```

---

## Requirements

- Java 25
- Maven
- Python 3
- Matplotlib

---

## Build the Project

From the project root, run:

```bash
mvn compile
```

---

## Run Tests

Run all JUnit tests with:

```bash
mvn test
```

All tests should pass successfully.

---

## Run the Benchmark

The benchmark can be run from IntelliJ by running:

```text
daa.Main
```

or from the command line after compilation.

The benchmark runs the following workloads:

- W1 - Random Access
- W2 - Search
- W3 - Insert and Remove
- W4 - Priority Processing

Input sizes:

```text
100
1,000
10,000
100,000
```

These sizes form a geometric progression with ratio 10.

The benchmark uses:

```java
new Random(42)
```

to make the generated data reproducible.

Each case is executed using:

- 1 warm-up run
- 5 measured runs
- median measured execution time

Execution time is stored as a `double` in milliseconds.

---

## Generate Plots

After running the benchmark, run:

```bash
python make_plots.py
```

The generated images will be saved in:

```text
results/plots/
```

The plots use logarithmic scaling on both the X-axis and Y-axis.

---

## CSV Format

The CSV file contains the following columns:

```text
workload,variant,structure,n,time_ms,steps,moves,comparisons
```

---

## Project Structure

```text
src/main/java/daa/
├── Main.java
├── bench/
│   └── Benchmark.java
├── ds/
│   ├── IntList.java
│   ├── DynamicArray.java
│   ├── MyLinkedList.java
│   └── MinHeap.java
└── metrics/
    ├── Metrics.java
    ├── Result.java
    └── CsvWriter.java

src/test/java/daa/
├── DynamicArrayTest.java
├── MyLinkedListTest.java
└── MinHeapTest.java
```

---

## Report

The full analysis is available in:

```text
REPORT.md
```

It includes:

- complexity analysis
- loop invariant proofs
- benchmark results
- plots
- discussion of performance trade-offs