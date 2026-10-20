---
type: concept
title: Wrapper class
summary: The object version of a primitive (Integer for int), needed because generics hold objects.
sessions: [04-collections-generics]
status: complete
---

# Wrapper class

The object version of a primitive (`Integer` for `int`), needed because generics hold objects.

## How it works

A [primitive type](primitive-type.md) such as `int` holds a plain value and is not an [object](object.md). Some places need an object: a type argument such as `ArrayList<...>` ([generics](generics.md)) must be a class. For this, each primitive has a **wrapper class** that holds one value of that type inside an object.

| Primitive | Wrapper |
|---|---|
| `int` | `Integer` |
| `double` | `Double` |
| `boolean` | `Boolean` |
| `char` | `Character` |
| `long` | `Long` |

The others (`byte`, `short`, `float`) follow the same pattern. Note the odd names for `int` and `char`.

### What a wrapper gives you

- A home for the value inside a collection: `ArrayList<Integer>`, `HashMap<String, Double>`.
- Methods and constants: `Integer.parseInt("42")` turns text into an `int`, `Integer.MAX_VALUE` is the largest `int`, and `Double.compare(a, b)` compares two numbers.
- The value `null`. An `Integer` variable can hold "no value"; an `int` cannot.

### Immutable

A wrapper object cannot be changed once it exists, like a `String`; see [immutability](immutability.md). Adding one to an `Integer` makes a new `Integer`.

### Boxing happens for you

You rarely write `Integer.valueOf(5)` yourself, because Java converts between the primitive and the wrapper automatically. That is [autoboxing](autoboxing.md).

### When to use the primitive

Use `int` for ordinary numbers and calculations. It is simpler and faster, and cannot be `null`. Use `Integer` only where an object is required, mainly in collections.

## Example

```java
import java.util.ArrayList;

void main() {
    ArrayList<Integer> scores = new ArrayList<>();
    scores.add(70);
    scores.add(85);

    int total = 0;
    for (int score : scores) {
        total += score;
    }
    IO.println(total);

    IO.println(Integer.parseInt("42") + 1);
    IO.println(Integer.MAX_VALUE);
}
```

Output:

```
155
43
2147483647
```

## Common confusions

### "`Integer` and `int` are the same"

They are different types. `int` is a plain value; `Integer` is an object that can be `null`. Java converts between them, so the difference is easy to forget, until an `Integer` that is `null` is unboxed and the program stops with a `NullPointerException`.

### "Compare wrappers with `==`"

`==` on two `Integer` objects compares the references, not the values. See [autoboxing](autoboxing.md). Use `.equals()`.

### "`int` has methods"

It does not; `Integer` does. `5.toString()` is invalid, while `Integer.toString(5)` and `Integer.valueOf(5).toString()` work.

> [!TIP]
> **From Python:**
> Python has no equivalent, because every number there is already an object. Java has two kinds of integer: the plain `int` and the object `Integer`.

> [!TIP]
> **From C#:**
> C# has `int?` for a nullable integer and boxes into `object`. In Java the closest thing to `int?` is `Integer`, and `List<int>` is not allowed.

## Further reading

- [Java Tutorials: Numbers and wrapper classes](https://docs.oracle.com/javase/tutorial/java/data/numbers.html): the official introduction.
