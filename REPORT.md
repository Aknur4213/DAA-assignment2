## 3. Loop Invariant Proofs

### 3.1 `DynamicArray.contains(x)`

The `contains(x)` method checks the elements of the array from left to right.

```java
for (int i = 0; i < size; i++) {
    int value = data[i];
    metrics.addStep();

    metrics.addComparison();

    if (value == x) {
        return true;
    }
}

return false;
```

#### Invariant

Before each iteration with index `i`, none of the elements from index `0` to index `i - 1` is equal to `x`.

#### Initialization

Before the first iteration, `i = 0`.

No elements have been checked yet, so there are no elements before index `0` that could be equal to `x`.

Therefore, the invariant is true before the first iteration.

#### Maintenance

During iteration `i`, the method checks `data[i]`.

If `data[i] == x`, the method returns `true`, which is the correct result.

If `data[i] != x`, then every element from index `0` to index `i` has now been checked and is not equal to `x`.

Therefore, before the next iteration, the invariant is still true.

#### Termination

The loop terminates when `i == size`.

At that point, every element from index `0` to index `size - 1` has been checked.

If the method has not returned `true`, then `x` does not exist in the array, so returning `false` is correct.

#### Conclusion

The invariant proves that `contains(x)` checks every stored element and correctly returns `true` if `x` is found and `false` otherwise.

---

### 3.2 `MinHeap.bubbleDown()`

The `bubbleDown()` method restores the min-heap property after `extractMin()` moves the last heap element to the root.

```java
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
```

#### Invariant

Before each iteration, all parts of the heap satisfy the min-heap property except possibly the current node in relation to its children.

The subtrees below the current node are already valid min-heaps.

#### Initialization

After `extractMin()`, the last element is moved to index `0`.

Before this change, the heap satisfied the min-heap property.

Moving the last element to the root can only create a violation between the new root and its children. The subtrees below the root are still valid min-heaps.

Therefore, the invariant is true before the first iteration.

#### Maintenance

During each iteration, the algorithm calculates the indexes of the left and right children.

It then chooses the smaller child.

If the current value is less than or equal to the smaller child, then the current node is also less than or equal to both children, so the heap property is correct and the loop stops.

Otherwise, the current node is swapped with the smaller child.

After the swap, the heap property is correct at the old position of the current node.

The value that moved downward may still violate the heap property with its new children.

The variable `current` is then updated to the position of that moved value.

Therefore, before the next iteration, the only possible violation is again between the current node and its children, so the invariant is preserved.

#### Termination

The loop stops in one of two situations:

1. `left >= size`, which means the current node has no children.
2. `currentValue <= childValue`, which means the current node is less than or equal to its smallest child.

If the node has no children, it cannot violate the heap property below itself.

If the current node is less than or equal to its smallest child, then it is also less than or equal to the other child.

Therefore, when the loop terminates, the min-heap property holds throughout the entire heap.

#### Conclusion

The loop invariant proves that `bubbleDown()` correctly moves the possible heap violation downward until the min-heap property is completely restored.
## 4. Complexity Analysis

The following tables show the best-case, average-case, and worst-case time complexities of the implemented data structures.

### 4.1 DynamicArray

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space | Justification |
|---|---:|---:|---:|---:|---|
| `add(x)` | Θ(1) | Θ(1) amortized | Θ(n) | O(n) | Normally the value is added directly at the end. When the internal array is full, a new array with double capacity is created and all existing elements are copied. |
| `add(index, x)` | Θ(1) | Θ(n) | Θ(n) | O(n) | Adding at the end requires no shifting, but inserting near the beginning or middle requires shifting elements to the right. A resize may also require copying all elements. |
| `remove(index)` | Θ(1) | Θ(n) | Θ(n) | Θ(1) | Removing the last element requires no shifting. Removing from the beginning or middle requires shifting later elements one position to the left. |
| `get(index)` | Θ(1) | Θ(1) | Θ(1) | Θ(1) | Arrays provide direct access to an element using its index. |
| `contains(x)` | Θ(1) | Θ(n) | Θ(n) | Θ(1) | The value may be found in the first position, but in the average or worst case many or all elements must be checked. |
| `size()` | Θ(1) | Θ(1) | Θ(1) | Θ(1) | The current number of elements is stored directly in the `size` field. |

#### DynamicArray Notes

The most important advantage of `DynamicArray` is constant-time indexed access.

```text
get(index) = Θ(1)
```

The main disadvantage is that insertions and removals near the beginning or middle require element shifting.

The `add(x)` operation is **amortized Θ(1)** because most additions require only one insertion. Occasionally, when the array becomes full, the capacity is doubled and all existing elements are copied into the new array.

---

### 4.2 MyLinkedList

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space | Justification |
|---|---:|---:|---:|---:|---|
| `add(x)` | Θ(1) | Θ(1) | Θ(1) | Θ(1) | The list stores a `tail` reference, so a new node can be attached directly to the end. |
| `add(index, x)` | Θ(1) | Θ(n) | Θ(n) | Θ(1) | Adding at index `0` only changes a few references. For other indexes, the list must traverse nodes to find the previous node. |
| `remove(index)` | Θ(1) | Θ(n) | Θ(n) | Θ(1) | Removing the head only changes references. Removing from another position requires traversing the list to find the previous node. |
| `get(index)` | Θ(1) | Θ(n) | Θ(n) | Θ(1) | Index `0` can be accessed immediately, but other indexes require following node references from the head. |
| `contains(x)` | Θ(1) | Θ(n) | Θ(n) | Θ(1) | The value may be stored in the first node, but otherwise the method may need to examine many or all nodes. |
| `size()` | Θ(1) | Θ(1) | Θ(1) | Θ(1) | The number of nodes is stored directly in the `size` field. |

#### MyLinkedList Notes

The main advantage of `MyLinkedList` is fast insertion and removal at the head.

```text
add(0, x) = Θ(1)
remove(0) = Θ(1)
```

However, random indexed access is slower because the list does not support direct indexing.

For example, to reach index `i`, the program must start from `head` and repeatedly follow the `next` reference.

Therefore:

```text
get(index) = O(n)
```

in the worst case.

---

### 4.3 MinHeap

| Operation | Best Case | Average Case | Worst Case | Auxiliary Space | Justification |
|---|---:|---:|---:|---:|---|
| `insert(x)` | Θ(1) | O(log n) | Θ(log n) | O(n) | The new value may already satisfy the heap property, but in the worst case it bubbles from the bottom of the heap to the root. A resize may temporarily require a new array. |
| `peekMin()` | Θ(1) | Θ(1) | Θ(1) | Θ(1) | The minimum element of a min-heap is always stored at index `0`. |
| `extractMin()` | Θ(1) | O(log n) | Θ(log n) | Θ(1) | With one element, removal is immediate. Otherwise, the last value is moved to the root and may need to bubble down through the height of the heap. |
| `size()` | Θ(1) | Θ(1) | Θ(1) | Θ(1) | The number of heap elements is stored directly in the `size` field. |

#### MinHeap Notes

A binary heap has height proportional to:

```text
log n
```

Therefore, both `insert(x)` and `extractMin()` may need to move an element through approximately `log n` levels.

The minimum value is always stored at the root:

```text
heap[0]
```

so `peekMin()` takes constant time.

---

### 4.4 Overall Comparison

| Operation / Use Case | DynamicArray | MyLinkedList | MinHeap |
|---|---:|---:|---:|
| Random indexed access | Θ(1) | O(n) | Not used for indexed access |
| Search with `contains` | O(n) | O(n) | Not implemented |
| Insert at head | O(n) | Θ(1) | Not applicable |
| Remove at head | O(n) | Θ(1) | Not applicable |
| Insert at middle | O(n) | O(n) | Not applicable |
| Access minimum | Not supported directly | Not supported directly | Θ(1) |
| Insert by priority | Not applicable | Not applicable | O(log n) |
| Remove minimum | Not applicable | Not applicable | O(log n) |

Although `DynamicArray` and `MyLinkedList` can sometimes have the same asymptotic complexity, their actual running times may still be different. `DynamicArray` stores primitive values in contiguous memory, which provides better CPU cache locality. `MyLinkedList` stores separate node objects connected by references, so traversal requires pointer chasing and may cause more cache misses.

## 5. Benchmark Results and Plots

The benchmark was executed for the following input sizes:

```text
100
1,000
10,000
100,000
```

These values form a geometric progression with ratio `10`.

All benchmark data was generated using:

```java
new Random(42)
```

to make the experiment reproducible.

Each benchmark case used:

- 1 warm-up run, which was discarded;
- 5 measured runs;
- the median of the 5 measured execution times.

Execution time was measured using `System.nanoTime()` and converted to milliseconds using:

```java
double timeMs = (end - start) / 1_000_000.0;
```

Therefore, `time_ms` is stored as a `double` and can contain values smaller than one millisecond.

The plots use logarithmic scaling on both axes:

```python
ax.set_xscale("log", base=10)
ax.set_yscale("log", base=10)
```

This makes multiplicative growth easier to compare. A value of `0` cannot be displayed on a logarithmic axis, so zero-valued operation counts remain unchanged in `results.csv` but are not plotted.

---

### 5.1 W1 - Random Access

In this workload, both `DynamicArray` and `MyLinkedList` were filled with `n` values.

Then 10,000 `get(index)` operations were performed using random indexes.

Expected behavior:

- `DynamicArray` should be much faster because array indexing is direct.
- `MyLinkedList` must follow node references from the head to reach the requested index.
- `DynamicArray` should perform approximately one step per `get()` operation.
- `MyLinkedList` should perform an increasing number of traversal steps as `n` grows.

Insert the generated `W1.png` plot here.

---

### 5.2 W2 - Search

In this workload, 1,000 `contains(x)` queries were performed.

Half of the searched values were guaranteed to exist in the structure, while the other half were guaranteed to be absent.

Expected behavior:

- Both structures use linear search, so the operation count grows with `n`.
- `DynamicArray` may still be faster in real execution time because its values are stored contiguously in memory.
- `MyLinkedList` follows references between separate node objects, which introduces pointer-chasing overhead.

Insert the generated `W2.png` plot here.

---

### 5.3 W3 - Insert and Remove

This workload contains two variants:

- `head`
- `middle`

For each variant, 1,000 insertions and 1,000 removals were performed.

#### Head

For the head variant, all operations use index `0`.

Expected behavior:

- `DynamicArray` must shift many existing elements.
- `MyLinkedList` only needs to update a small number of references.

Therefore, `MyLinkedList` should perform significantly better for head insertion and removal.

#### Middle

For the middle variant, the index is recalculated using:

```java
list.size() / 2
```

before each operation.

Expected behavior:

- `DynamicArray` must shift many elements.
- `MyLinkedList` must traverse nodes to reach the middle.

Both structures therefore require work proportional to `n`, although their actual execution times may differ.

Insert the generated `W3.png` plot here.

---

### 5.4 W4 - Priority Processing

In this workload, `n` values were inserted into `MinHeap`.

Then `extractMin()` was called `n` times.

The returned values were checked to ensure that they appeared in non-decreasing order.

Expected behavior:

- `insert(x)` may perform bubble-up and takes at most `O(log n)`.
- `extractMin()` may perform bubble-down and takes at most `O(log n)`.
- Processing all `n` values therefore grows approximately as `O(n log n)`.

Insert the generated `W4.png` plot here.
## 6. Discussion

The benchmark results show that the choice of data structure has a major effect on performance.

`DynamicArray` is much faster than `MyLinkedList` for random indexed access because an array can access `data[index]` directly in constant time.

`MyLinkedList` must start from the head and follow node references until it reaches the requested index, so `get(index)` can require many traversal steps.

Even when two operations have the same Big-O complexity, their real execution times can still be different.

`DynamicArray` stores primitive `int` values close together in contiguous memory, which gives good spatial locality and allows the CPU cache to work efficiently.

`MyLinkedList` stores separate node objects connected by references, so traversing the list requires pointer chasing between different memory locations.

This can cause more cache misses and make linked-list traversal slower than array traversal.

Linked-list nodes also have additional memory overhead because each node is an object that stores both a value and a reference.

Creating many node objects can also increase garbage collection pressure in the JVM.

However, `MyLinkedList` performs very well for insertion and removal at the head because only a small number of references need to be changed.

In comparison, `DynamicArray` must shift many elements when inserting or removing near the beginning or middle.

`DynamicArray` is therefore a better choice when fast indexed access, iteration, and good cache locality are important.

`MyLinkedList` is more useful when frequent insertions and removals are performed at the head or when the required node is already known.

`MinHeap` is the best choice when values must be processed according to priority because the minimum value can be accessed in constant time and insertion and removal require at most logarithmic time.

Overall, the benchmark demonstrates that Big-O complexity is important, but memory layout and the actual operations performed by a data structure also strongly affect real running time.