---
type: concept
title: Collection
summary: An object that holds a group of other objects; ArrayList keeps an ordered list, HashMap looks values up by key.
sessions: [04-collections-generics]
status: complete
---

# Collection

An object that holds a group of other objects; `ArrayList` keeps an ordered list, `HashMap` looks values up by key.

## How it works

An [array](array.md) has a fixed size. Most real programs need a group that changes: the cards in a hand, the names of the players, the number of times each word appeared. The Java standard library (the package `java.util`) offers ready-made *collections* for this. A collection is an [object](object.md) like any other, with methods such as `add` and `size`, and what it holds is other objects.

The two collections used in this course are:

| | `ArrayList<E>` | `HashMap<K, V>` |
|---|---|---|
| What it is | An ordered list | A set of key-value pairs |
| Find an item by | position (`get(2)`) | key (`get("Ada")`) |
| Duplicates | Allowed | Keys are unique; a repeated key replaces the old value |
| Order | The order you added things | No promised order |

Both are written with angle brackets (`ArrayList<String>`, `HashMap<String, Integer>`) that say what they hold. That is [generics](generics.md).

### ArrayList

An `ArrayList` is a list that grows. Inside it keeps an ordinary array and, when the array is full, replaces it with a bigger one and copies the elements over. You never see this.

- `add(x)` appends; `add(i, x)` inserts at position `i`.
- `get(i)` reads and `set(i, x)` replaces.
- `remove(i)` deletes by position and shifts later elements left.
- `size()` is the number of elements, `isEmpty()` asks whether there are none and `contains(x)` searches.

Indexes start at 0, and an index outside `0` to `size() - 1` throws an `IndexOutOfBoundsException`. The *for-each* loop (`for (String name : names)`) visits each element in order.

### HashMap

A `HashMap` stores pairs: a **key** and the **value** that belongs to it. `put(key, value)` stores a pair, and `get(key)` finds the value quickly, without searching through everything. If the key is not there, `get` returns `null`, and `containsKey(key)` tells you whether it is. Putting a pair with a key that already exists *replaces* the old value.

The name comes from *hashing*, a trick that turns the key into a number that tells the map where to look. You do not need the details, but one consequence matters: the map makes **no promise about the order** of its entries. Do not write code that depends on it.

### Choosing

Use a **list** when you have a group in order and mostly go through all of it. Use a **map** when you ask "what belongs to *this* key?". If you have two or three values with fixed meanings, such as a name and an age, you want neither: use two variables, or a [class](class.md). See [KISS](kiss.md).

### Collections hold references

A collection of objects holds [references](reference-vs-value.md), not copies. Two lists that contain the same `Card` object see the same card. A collection of numbers holds `Integer` objects, not `int` values; see [wrapper classes](wrapper-class.md).

## Example

```java
import java.util.*;

void main() {
    ArrayList<String> names = new ArrayList<>();
    names.add("Ada");
    names.add("Grace");
    names.add("Ada");
    IO.println(names + " has " + names.size() + " items");
    IO.println("first: " + names.get(0));

    HashMap<String, Integer> ages = new HashMap<>();
    ages.put("Ada", 36);
    ages.put("Grace", 45);
    ages.put("Ada", 37);
    IO.println("Ada is " + ages.get("Ada"));
    IO.println("map has " + ages.size() + " entries");
    IO.println("Linus: " + ages.get("Linus"));
}
```

Output:

```
[Ada, Grace, Ada] has 3 items
first: Ada
Ada is 37
map has 2 entries
Linus: null
```

The list keeps the duplicate `Ada`. The map keeps one entry per key, and the second `put("Ada", ...)` replaced `36` with `37`. The missing key gives `null`.

## Common confusions

### "`size` is a field, like `length`"

An array has `length` without parentheses; a collection has `size()` with them. Strings also use `length()`. It is a historical inconsistency, and the compiler tells you when you get it wrong.

### "Removing inside a for-each is fine"

Changing a list while a for-each loop walks it usually throws a `ConcurrentModificationException`. Collect what to remove, or use an index loop and be careful with the shifting.

### "`remove(1)` removes the value 1"

On a list of numbers, `remove(1)` removes the element at **index** 1. To remove the value 1 write `remove(Integer.valueOf(1))`. See [autoboxing](autoboxing.md).

### "A HashMap keeps what I put in, in order"

It does not. Print the keys sorted, or look values up by key.

### "A map is always better than an if"

A `HashMap` with two entries known in advance is more code and more things to get wrong than two variables. Use the simplest tool that fits.

> [!TIP]
> **From Python:**
> `ArrayList` is a `list`, and `HashMap` is a `dict`, with two differences: every key and value has one declared type, and you write `map.get(key)` and `map.put(key, value)` instead of `d[key]`.

> [!TIP]
> **From C#:**
> `ArrayList<E>` is `List<T>` (`Add` becomes `add`, `Count` becomes `size()`), and `HashMap<K, V>` is `Dictionary<TKey, TValue>`. A missing key returns `null` in Java, while C# throws.

## Further reading

- [Java Tutorials: Collections](https://docs.oracle.com/javase/tutorial/collections/index.html): the official introduction.
- [ArrayList in the Java API](https://docs.oracle.com/en/java/javase/): in the API documentation, look for `java.util.ArrayList` and `java.util.HashMap`.
