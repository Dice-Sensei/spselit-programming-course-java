---
type: session
title: Methods, arrays and strings
session: 2
status: skeleton
blocks:
  - {name: methods, status: planned}
  - {name: arrays, status: planned}
  - {name: strings, status: planned}
  - {name: formatting, status: planned, optional: true}
  - {name: grids, status: planned, optional: true}
concepts: [method-signature, overloading, array, reference-vs-value, immutability, string]
---

# S2 — Methods, arrays and strings

## Goal

By the end of this session you can write your own methods with parameters and return values, store many values in an array, and work with text using `String` methods. You also know why two strings must be compared with `.equals()` and not with `==`.

> [!NOTE]
> This page works on its own: you do not need S1 to follow it. You need JDK 25 or newer; if `java Hello.java` fails with an error mentioning "preview" or "unnamed class", see the setup guide's [common problems](../../setup.md#common-problems).

## Methods

A method is a named piece of code that you can run as often as you like. Until now, everything was in `main`; methods let you split a program into parts with names, and reuse them.

Look at this method:

```java
int square(int n) {
    return n * n;
}
```

`int` before the name is the **return type**: the type of the value the method gives back (`void` means it gives back nothing). `n` is a **parameter**, an input with a type. When you call `square(5)`, the value `5` (the **argument**) is *copied* into `n`, and the method returns `25`. Because it is a copy, a method can never change the caller's `int` variable.

The name together with the parameter types, here `square(int)`, is the [method signature](../../concepts/method-signature.md). The compiler uses it to find out which method you mean, and it is why `square("5")` is an error. The return type and the parameter names are not part of the signature.

A variable declared inside a method exists only inside it, which is called its *scope*. Two methods can each have their own variable called `result`.

Example: [Signatures.java](examples/Signatures.java)

### Overloading

You may have several methods with the same name if their parameter lists differ. This is [overloading](../../concepts/overloading.md): the compiler looks at the arguments and picks the version that fits. `IO.println` and `Math.max` are overloaded, which is why they work for many types.

```java
int max(int a, int b) { ... }
double max(double a, double b) { ... }
int max(int a, int b, int c) { ... }
```

Methods that differ only in return type, or only in parameter names, are not overloads, and the compiler rejects them.

Example: [Overloading.java](examples/Overloading.java)

### Exercises

**Core**

- Write `boolean isEven(int n)` and call it for a few numbers.
- Write `max` for two ints and overload it for three ints.
- Write `String fizzBuzz(int n)` that *returns* `"FizzBuzz"` if `n` is divisible by both 3 and 5, `"Fizz"` if only by 3, `"Buzz"` if only by 5, and otherwise `n` as text. Then call it for the numbers 1 to 15 in a loop and print the results.

**Extra**

- Write `boolean isPrime(int n)`, then print all primes up to 50 using it.

**Challenge**

- Write `int power(int base, int exp)` without using `Math.pow`, then add an overload that takes a `double` base.

## Arrays

An [array](../../concepts/array.md) is a fixed-size, ordered sequence of values of the same type. You reach each value by its index, counted from **0**.

```java
int[] scores = {70, 85, 90};
```

`scores[0]` is `70` and `scores.length` is `3` (no parentheses). `new int[3]` creates three elements with the default value `0`. The size cannot change after the array is created. Printing an array directly gives something unreadable; `Arrays.toString(scores)` gives `[70, 85, 90]`, and `Arrays.sort(scores)` sorts it in place.

Valid indexes are `0` to `length - 1`. Anything else stops the program:

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3
	at Uncaught.main(Uncaught.java:3)
```

Read it from the left: the kind of error, the index you used, the length of the array, and the line number where it happened.

Example: [ArrayBasics.java](examples/ArrayBasics.java)

> [!NOTE]
> We loop over arrays with the indexed `for` loop (`for (int i = 0; i < scores.length; i++)`). A shorter loop for going through all elements comes in S4.

### Arrays and methods: reference vs value

Remember that a method gets a *copy* of each argument. For an `int`, the copy is the number, so the method cannot change your variable. For an array, the variable holds a *reference* to the array, and the copy is a copy of that reference: your variable and the parameter now refer to **the same array**. This is [reference vs value](../../concepts/reference-vs-value.md).

So a method that writes into the array changes it for the caller, but a method that assigns a *new* array to its parameter changes only its own copy:

```java
void doubleAll(int[] a) {
    for (int i = 0; i < a.length; i++) {
        a[i] = a[i] * 2;
    }
}

void replace(int[] a) {
    a = new int[]{0, 0, 0};
}
```

After `doubleAll(nums)` the caller's array has doubled values. After `replace(nums)` it is unchanged. The same applies to assignment: `int[] alias = nums;` gives two names for one array.

Example: [ReferenceVsValue.java](examples/ReferenceVsValue.java)

> [!TIP]
> **From Python:**
> A Java array looks like a Python list but has a fixed size, one element type and no negative indexes or slicing. In Python every variable is a reference; in Java only the non-primitive ones are.

### Exercises

**Core**

- Write `int sum(int[] numbers)` and `int max(int[] numbers)`, and test them on a few arrays.
- Predict the output, then run it. One method doubles every element of an array, another doubles an `int`. Which change does the caller see, and why?

**Extra**

- Array statistics: write methods for the minimum, maximum, mean and median of an array of numbers.
- Reverse an array in place.

**Challenge**

- Rotate an array by *k* positions in place, without creating a second array.

## Strings

A [string](../../concepts/string.md) is text: `"Java"`. It is an *object*, which means you call methods on it with a dot. (What objects are, and how to make your own, is S3; for now, a `String` is a value that comes with useful operations.) Like an array, a string is indexed from 0.

```java
String s = "Java";
s.length()          // 4  (with parentheses, unlike array.length)
s.charAt(0)         // 'J'
s.substring(1, 3)   // "av": from index 1 up to, but not including, 3
s.indexOf('v')      // 2  (-1 if not found)
s.toUpperCase()     // "JAVA"
```

Example: [StringBasics.java](examples/StringBasics.java)

### Strings are immutable

A string never changes after it is created. We say it is [immutable](../../concepts/immutability.md). A method such as `toUpperCase()` does not change the string; it returns a **new** one. So this line does nothing useful:

```java
s.toUpperCase();      // the new string is thrown away
```

and you have to keep the result: `s = s.toUpperCase();`. The variable now refers to a new string. The old string is untouched, which is why strings can be shared safely.

Example: [Immutability.java](examples/Immutability.java)

### Comparing strings: `==` or `.equals()`?

A `String` variable holds a reference, just like an array variable. So `==` asks "are these the *same object*?", not "is the text the same?". To compare text, always use `.equals()`:

```java
String a = "hi";
String b = new String("hi");
a == b          // false: two separate objects
a.equals(b)     // true: same characters
```

`==` sometimes seems to work, because identical literals in the source code are shared. That is a trap: it stops working as soon as the text is built while the program runs. Use `.equals()` for text, every time. (For a `char`, which is a primitive, `==` is correct.)

Example: [StringEquality.java](examples/StringEquality.java)

> [!TIP]
> **From Python:**
> In Python, `==` compares values and `is` compares identity. In Java it is the other way around in spirit: `.equals()` compares the text, and `==` is the identity check.

> [!TIP]
> **From C#:**
> In C#, `==` on strings compares the text. In Java it does not, so use `.equals()`.

### Characters

A single character has the type `char` and is written in single quotes: `'J'`. `"J"` is a string, `'J'` is a `char`. A `char` is also a number (its code), so you can calculate with it: `(char) ('c' + 1)` is `'d'`, and `'c' - 'a'` is `2`. Together with `charAt(i)` in a loop, this is all you need to count letters or shift them.

### Exercises

**Core**

- Write `boolean isPalindrome(String text)`. Test it with `"level"`, `"Java"` and the empty string.
- Write `int countVowels(String text)` that counts `a e i o u`, ignoring case.
- Predict the output, then run it: compare two strings with `==` and with `.equals()` (one created with `new String("hi")`), and call `toUpperCase()` on a string without assigning the result.

**Extra**

- Count the words in a sentence using only `indexOf` and `charAt` (the `split` method comes later).
- Write a method that capitalises the first letter of each word in a sentence.

**Challenge**

- Caesar cipher: write `String encrypt(String text, int shift)` that moves each letter `shift` places forward in the alphabet and wraps from `z` to `a`, keeping other characters as they are. Then write `decrypt`.

## Formatting output (if time allows)

Joining text with `+` gets awkward when you want aligned columns or a fixed number of decimals. `String.format` builds a string from a *template* with placeholders:

```java
String.format("%s has %d points", "Ada", 42)    // "Ada has 42 points"
String.format("%5.1f C = %6.1f F", 50.0, 122.0) // " 50.0 C =  122.0 F"
```

| Placeholder | Meaning |
|---|---|
| `%d` | a whole number (`int`, `long`) |
| `%s` | any value as text |
| `%f` | a decimal number (`double`) |
| `%5d`, `%6.1f` | a minimum **width** (padded with spaces on the left); `.1` is the number of **decimals** |
| `%-10s` | the minus sign aligns left instead of right |
| `%05d` | pads with zeros |
| `%%` | a literal percent sign |

The arguments must fit the placeholders: a `%d` with a text argument stops the program with an `IllegalFormatConversionException`, and too few arguments give a `MissingFormatArgumentException`.

> [!NOTE]
> `%f` follows the **language settings of the computer**. On a computer set to Czech you get a decimal **comma** (`50,0`), not a point (`50.0`). Both are correct. If you need a point everywhere, write `String.format(Locale.US, "%.1f", x)`.

> [!NOTE]
> **Česky:**
> Formátování čísel podle `%f` používá nastavení jazyka počítače, takže na českém počítači uvidíš desetinnou čárku místo tečky.

Example: [Formatting.java](examples/Formatting.java)

### Exercises

**Core**

- Print the Celsius-to-Fahrenheit table (for example 0 to 100 in steps of 10) with aligned columns, using `String.format`.

## Grids (if time allows)

A two-dimensional array is an *array of arrays*: a table with rows and columns.

```java
int[][] table = new int[3][3];     // 3 rows, 3 columns, all 0
table[1][2] = 6;                   // row 1, column 2
```

`table.length` is the number of rows, and `table[i].length` is the length of row `i`. Each row is itself an array, so you use two nested loops to visit every cell, and `Arrays.toString(table[i])` prints one row. (`Arrays.deepToString(table)` prints the whole table at once.)

Example: [Grids.java](examples/Grids.java)

### Exercises

**Core**

- Create a 3×3 grid, fill it with the multiplication table (`(row + 1) * (column + 1)`), and print it row by row.

**Extra**

- Find the largest value in a grid and print where it is (its row and column).

## Lecture notes

<!-- filled after the lecture -->
