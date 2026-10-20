---
type: concept
title: Autoboxing
summary: Java's automatic conversion between a primitive and its wrapper object.
sessions: [04-collections-generics]
status: complete
---

# Autoboxing

Java's automatic conversion between a primitive and its wrapper object.

## How it works

Each primitive has a [wrapper class](wrapper-class.md), and the compiler converts between the two when the types call for it:

- **Boxing** (`int` to `Integer`): `Integer x = 5;` and `list.add(5)` on an `ArrayList<Integer>`.
- **Unboxing** (`Integer` to `int`): `int y = list.get(0);` and `total += list.get(0);`.

This is why `ArrayList<Integer>` feels like a list of plain numbers. The compiler writes the conversion for you; behind `list.add(5)` is `list.add(Integer.valueOf(5))`.

### Where it hurts

Boxing and unboxing are silent, which hides three problems:

- **`null` unboxing.** Unboxing an `Integer` that is `null` throws a `NullPointerException`. A `HashMap<String, Integer>` returns `null` for a missing key, so `int n = counts.get("xyz");` stops the program.
- **`==` compares references.** For two `Integer` objects, `==` asks whether they are the *same object*, not whether the numbers are equal. See below.
- **Speed.** A boxed number is an object on the heap. Millions of them cost more than millions of `int`s.

### The `Integer` cache

`Integer.valueOf` keeps ready-made objects for small values, from -128 to 127, and hands out the same one every time. So two `Integer`s holding `100` are the same object, and `==` says `true`. Two holding `1000` are separate objects, and `==` says `false`. The values are equal in both cases. Never rely on this: compare wrapper objects with `.equals()`. It is the same lesson as comparing `String`s in S2.

### `remove` on a list of numbers

`list.remove(1)` on an `ArrayList<Integer>` removes the element at **index** 1, because the `int` version of the method is a better match than boxing. To remove the *value* 1, write `list.remove(Integer.valueOf(1))`.

## Example

```java
import java.util.ArrayList;
import java.util.HashMap;

void main() {
    Integer a = 100;
    Integer b = 100;
    IO.println(a == b);
    IO.println(a.equals(b));

    Integer c = 1000;
    Integer d = 1000;
    IO.println(c == d);
    IO.println(c.equals(d));

    ArrayList<Integer> list = new ArrayList<>();
    list.add(10);
    list.add(5);
    list.add(1);
    list.remove(1);
    IO.println(list);

    HashMap<String, Integer> counts = new HashMap<>();
    try {
        int n = counts.get("xyz");
    } catch (NullPointerException e) {
        IO.println("unboxing null");
    }
}
```

Output:

```
true
true
false
true
[10, 1]
unboxing null
```

## Common confusions

### "`==` works on `Integer` because it worked once"

It works for values from -128 to 127 because of the cache, and fails above. Use `.equals()`.

### "An `Integer` can never be `null`"

It can, and unboxing a `null` throws a `NullPointerException`.

### "Boxing is free"

It creates objects. It is fine in ordinary code, but visible in loops with millions of numbers.

> [!TIP]
> **From Python:**
> There is nothing to compare with, because Python numbers are already objects. In Java, remember that `Integer` is an object and `int` is not.

> [!TIP]
> **From C#:**
> C# also boxes, but `List<int>` stores plain `int`s there with no boxing. In Java, `List<Integer>` boxes every element.

## Further reading

- [Java Tutorials: Autoboxing and unboxing](https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html): the official explanation.
