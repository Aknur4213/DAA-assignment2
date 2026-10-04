import csv
from collections import defaultdict
from pathlib import Path

import matplotlib.pyplot as plt


CSV_FILE = Path("results/results.csv")
OUTPUT_DIR = Path("results/plots")


def load_results():
    rows = []

    with open(CSV_FILE, newline="", encoding="utf-8") as file:
        reader = csv.DictReader(file)

        for row in reader:
            row["n"] = int(row["n"])
            row["time_ms"] = float(row["time_ms"])
            row["steps"] = int(row["steps"])
            row["moves"] = int(row["moves"])
            row["comparisons"] = int(row["comparisons"])
            rows.append(row)

    return rows


def make_series_name(row):
    if row["variant"] == "-":
        return row["structure"]
    return f'{row["structure"]} / {row["variant"]}'


def setup_axes(ax, ylabel):
    ax.set_xscale("log", base=10)
    ax.set_yscale("log", base=10)
    ax.set_xlabel("n (input size)")
    ax.set_ylabel(ylabel)
    ax.grid(True, which="both", alpha=0.3)


def plot_workload(rows, workload, title, file_name):
    workload_rows = [row for row in rows if row["workload"] == workload]

    grouped = defaultdict(list)
    for row in workload_rows:
        grouped[make_series_name(row)].append(row)

    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(10, 12))

    # -------------------------
    # Top graph: Time vs n
    # -------------------------
    for name, values in grouped.items():
        values.sort(key=lambda r: r["n"])

        x = []
        y = []

        for row in values:
            if row["time_ms"] > 0:
                x.append(row["n"])
                y.append(row["time_ms"])

        if x:
            ax1.plot(x, y, marker="o", label=name)

    setup_axes(ax1, "Time (ms)")
    ax1.set_title(f"{title} - Time vs n")
    ax1.legend()

    # -------------------------
    # Bottom graph: Operations vs n
    # -------------------------
    metrics = ["steps", "moves", "comparisons"]

    for metric in metrics:
        for name, values in grouped.items():
            values.sort(key=lambda r: r["n"])

            x = []
            y = []

            for row in values:
                value = row[metric]
                if value > 0:   # log scale cannot show 0
                    x.append(row["n"])
                    y.append(value)

            if x:
                ax2.plot(x, y, marker="o", label=f"{name} / {metric}")

    setup_axes(ax2, "Operations")
    ax2.set_title(f"{title} - Operations vs n")
    ax2.legend()

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    plt.tight_layout()
    plt.savefig(OUTPUT_DIR / file_name, dpi=160)
    plt.close()


def main():
    rows = load_results()

    plot_workload(
        rows,
        "W1_random_access",
        "W1 Random Access",
        "W1.png"
    )

    plot_workload(
        rows,
        "W2_search",
        "W2 Search",
        "W2.png"
    )

    plot_workload(
        rows,
        "W3_insert_remove",
        "W3 Insert & Remove",
        "W3.png"
    )

    plot_workload(
        rows,
        "W4_priority_processing",
        "W4 Priority Processing",
        "W4.png"
    )

    print("Plots saved to:")
    print(OUTPUT_DIR.resolve())


if __name__ == "__main__":
    main()