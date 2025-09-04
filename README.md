# CustomLinkedList (Java)

A generic singly linked list implementation in Java. Not thread-safe.  
Provides **O(1)** operations at the ends and **O(n)** indexed operations.

---

## Features
- Generic type parameter (`LinkedList<T>`)
- `addFirst`, `addLast`, `add(index, value)`
- `getFirst`, `getLast`, `get(index)`
- `removeFirst`, `removeLast`, `remove(index)`
- `size()`
- Allows null elements

---

## Complexity (amortized)
- **addFirst / addLast / removeFirst / removeLast**: O(1)
- **get / add(index, value) / remove(index)**: O(n)

---

## API Summary
- `int size()`
- `T getFirst()` — throws `IllegalStateException` if empty
- `T getLast()` — throws `IllegalStateException` if empty
- `T get(int index)` — throws `IndexOutOfBoundsException` if index ∉ [0, size-1]
- `void addFirst(T value)`
- `void addLast(T value)`
- `void add(int index, T value)`
  - Inserts before the element at index.
  - index == 0 → `addFirst(value)`
  - index == size → `addLast(value)`
  - Otherwise throws `IndexOutOfBoundsException` if index ∉ [0, size]
- `T removeFirst()` — throws `IllegalStateException` if empty
- `T removeLast()` — throws `IllegalStateException` if empty
- `T remove(int index)` — throws `IndexOutOfBoundsException` if index ∉ [0, size-1]

---

## Usage

```java
package demo;

import customlist.LinkedList;

public class Demo {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(2);      // [2]
        list.addLast(3);       // [2, 3]
        list.add(1, 99);       // [2, 99, 3]

        System.out.println(list.getFirst()); // 2
        System.out.println(list.getLast());  // 3
        System.out.println(list.get(1));     // 99

        list.remove(1);        // [2, 3]
        list.removeFirst();    // [3]
        list.removeLast();     // []

        System.out.println(list.size());     // 0
    }
}
```

---

## Build and Run

- **Maven** (recommended):  
  Add source under `src/main/java` and test under `src/test/java`, then run:

```bash
mvn clean test
```

- **Plain javac** (example):  

```bash
# From project root
javac -d out src/main/java/customlist/LinkedList.java Demo.java
java -cp out demo.Demo
```

---

## Notes
- Index bounds are strictly checked.
- Implementation is optimized for operations at the ends.
- Not thread-safe — wrap externally if used across threads.
