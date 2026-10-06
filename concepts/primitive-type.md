---
type: concept
title: Primitive type
summary: A basic built-in type such as int or double that holds a plain value.
sessions: [01-kickoff]
status: draft
---

# Primitive type

A basic built-in type such as int or double that holds a plain value.

## How it works

Java has eight primitive types, including `int`, `long`, `double`, `boolean` and `char`. A variable of a primitive type stores the value itself. `String` is not primitive; it is an object.

### The eight types

| Type | Holds | Size | Range or values |
|---|---|---|---|
| `byte` | whole number | 8 bits | -128 to 127 |
| `short` | whole number | 16 bits | -32,768 to 32,767 |
| `int` | whole number | 32 bits | about ±2.1 billion (-2,147,483,648 to 2,147,483,647) |
| `long` | whole number | 64 bits | about ±9.2 × 10^18 |
| `float` | decimal number | 32 bits | about 7 significant digits |
| `double` | decimal number | 64 bits | about 15 significant digits |
| `char` | one UTF-16 code unit (a character) | 16 bits | 0 to 65,535 |
| `boolean` | truth value | not specified | `true` or `false` |

In practice, you will use `int` for whole numbers, `double` for decimals, `boolean` for conditions and `char` for single characters. Use `long` when `int` is too small (for example, for money in cents or for time in milliseconds). `byte`, `short` and `float` are for special cases.

### "Plain value"

A primitive variable *is* its value. `int age = 17;` reserves a few bytes for the variable `age` and puts the number 17 straight into them. Copying a primitive copies the number: after `int b = a;`, changing `a` does not change `b`.

Objects behave differently. A variable of an object type holds a *reference* to an object living elsewhere in memory (see [JVM](jvm.md)). We cover this in S2 as "reference vs value". Primitive types never need `new`, they cannot be `null`, and they have no methods: you cannot write `5.toString()`.

Primitive local variables live on the stack of the [JVM](jvm.md), so creating and discarding them is very cheap.

### Literals

How you write constant values in the source code:

| Type | Examples |
|---|---|
| `int` | `42`, `-7`, `1_000_000` (underscores help readability), `0xFF` (hexadecimal) |
| `long` | `42L` (the `L` suffix is required for numbers over about 2 billion) |
| `double` | `3.14`, `2.5e3` (2500.0) |
| `float` | `3.14f` |
| `char` | `'A'`, `'\n'` (newline) |
| `boolean` | `true`, `false` |

A decimal literal like `3.14` is a `double` by default. A whole-number literal is an `int` by default.

### Things that surprise people

**Overflow.** Whole-number types have a fixed size and silently *wrap around* when they run out. Java does not warn you:

```
Integer.MAX_VALUE + 1   →  -2147483648
```

**Integer division.** Dividing two `int`s gives an `int`, and the remainder is discarded: `7 / 2` is `3`, not `3.5`. To get `3.5`, at least one side must be a `double`: `7 / 2.0`. The remainder is available with `%`: `7 % 2` is `1`.

**Decimal numbers are approximate.** `double` stores numbers in binary, and most decimals such as `0.1` cannot be stored exactly. So `0.1 + 0.2` is `0.30000000000000004`. Do not compare doubles with `==`, and do not use them for money.

**`char` is a number.** `'A'` is the number 65 internally. `'A' + 1` is `66` (an `int`), and `(char) ('A' + 1)` is `'B'`.

## Example

```java
void main() {
    IO.println(Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE);
    IO.println(Long.MAX_VALUE);

    int big = Integer.MAX_VALUE;
    IO.println(big + 1);                       // -2147483648: overflow

    IO.println(1_000_000 * 1_000_000);         // -727379968: overflow in int
    IO.println(1_000_000L * 1_000_000L);       // 1000000000000: long is big enough

    IO.println(7 / 2);                         // 3
    IO.println(7 / 2.0);                       // 3.5
    IO.println(0.1 + 0.2);                     // 0.30000000000000004

    char c = 'A';
    IO.println(c + 1);                         // 66
    IO.println((char) (c + 1));                // B

    double d = 3.99;
    IO.println((int) d);                       // 3: the cast cuts, it does not round
    IO.println(10 / 0.0);                      // Infinity: no error for doubles
}
```

Run it and compare each result with the comment before you read on. The two overflow lines are exactly what the first-program extra exercise asks you to find.

## Common confusions

### Why is `String` not a primitive type?

Text has no fixed size, so it cannot be a simple value of a few bytes. `String` is a class, so a `String` variable holds a reference to an object, and it has methods like `"Java".length()`. That is why `String` is written with a capital letter and the primitives in lowercase.

### `int` and `Integer`

Every primitive has an object counterpart called a *wrapper class*: `int` ↔ `Integer`, `double` ↔ `Double`, `boolean` ↔ `Boolean`. These matter later when we use collections (S4), because a list can hold objects but not primitives.

### `long x = 3000000000;` does not compile

The literal `3000000000` is treated as an `int`, which is too small for it, so the compiler complains before the value is even stored in a `long`. Write `3000000000L`.

### `==` on doubles

Because of rounding, `0.1 + 0.2 == 0.3` is `false`. Compare with a tolerance: `Math.abs(a - b) < 0.000001`.

### Division by zero

For `int`, `10 / 0` throws an `ArithmeticException` at run time. For `double`, `10 / 0.0` is `Infinity` and no error is raised.

> [!TIP]
> **From Python:**
> Python's `int` has no size limit, so `2**100` just works, and overflow does not exist. Java's `int` is always 32 bits and wraps around; use `long` or `BigInteger` for more. Python's `/` always gives a decimal result (`7 / 2` is `3.5`, `7 // 2` is `3`). In Java the type decides: `7 / 2` is `3`. Python has no separate `char`; a one-letter string is just a string.

> [!TIP]
> **From C#:**
> Nearly identical: `int`, `long`, `double`, `bool` (called `boolean` in Java), `char`. A few things differ: Java has no unsigned types (no `uint`), no `decimal` (use `BigDecimal` for exact decimal arithmetic), and `byte` is signed, with range -128 to 127.

## Further reading

- [Java Tutorials: Primitive Data Types](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html): a short overview from the Java documentation.
