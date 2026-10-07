---
type: concept
title: Array
summary: A fixed-size, ordered sequence of values of the same type, accessed by index.
sessions: [02-methods-arrays-strings]
status: draft
---

# Array

A fixed-size, ordered sequence of values of the same type, accessed by index.

## How it works

An array holds many values under one name. All the values have the same type (an array of `int`, an array of `String`, ...), they are stored one after another, and you reach each one by its **index**: its position, counted from **0**. The size is fixed when the array is created and cannot change afterwards.

```java
int[] scores = {70, 85, 90};
```

`int[]` is the type "array of `int`". The array has three **elements**: `scores[0]` is `70`, `scores[1]` is `85` and `scores[2]` is `90`. The last valid index is always `length - 1`.

### Creating an array

There are two ways:

- **With values you already know:** `int[] scores = {70, 85, 90};`
- **With a size, to be filled in later:** `int[] scores = new int[3];` creates three elements, all set to a **default value**.

Default values depend on the element type:

| Element type | Default |
|---|---|
| `int`, `long`, `short`, `byte` | `0` |
| `double`, `float` | `0.0` |
| `boolean` | `false` |
| `char` | the character with code 0 (invisible) |
| any object type, such as `String` | `null` (no object yet) |

So `new int[3]` is `[0, 0, 0]`, and `new String[2]` is `[null, null]`. The size can be any `int` expression, for example `new int[n]`, but it is fixed from then on. A size of 0 is allowed and gives an empty array; a negative size throws a `NegativeArraySizeException`.

### Length, indexing, and looping

- `scores.length` is the number of elements. It is a field, not a method, so there are **no parentheses**. (This is different from `String`, where you write `s.length()`.)
- `scores[i]` reads element `i`, and `scores[i] = 5;` writes it.
- To process every element, the usual tool is a `for` loop over the indexes:

```java
int sum = 0;
for (int i = 0; i < scores.length; i++) {
    sum += scores[i];
}
```

Note the condition: `i < scores.length`, not `<=`. Getting this one off by one is the most common array mistake.

### What an array variable holds

An array is an object. The variable `scores` does **not** contain the numbers; it contains a *reference* to an array that lives in the heap (see the memory section of the [JVM](jvm.md) page). This has consequences that are covered in [reference vs value](reference-vs-value.md): if you assign one array variable to another, both variables refer to the *same* array, and a method that receives an array can change it.

### Useful tools in `java.util.Arrays`

The class `Arrays` has ready-made helpers (they are available without an `import` in our compact source files):

| Call | Does |
|---|---|
| `Arrays.toString(a)` | Turns the array into readable text: `[70, 85, 90]` |
| `Arrays.sort(a)` | Sorts the array in place, smallest first (for text, alphabetically) |
| `Arrays.fill(a, v)` | Sets every element to `v` |
| `Arrays.copyOf(a, n)` | Returns a **new** array of length `n` with the first elements copied; extra elements get the default value |
| `Arrays.equals(a, b)` | `true` if both arrays have the same elements in the same order |
| `Arrays.deepToString(g)` | Like `toString`, for arrays of arrays (see below) |

### Arrays of arrays

The element type of an array can itself be an array. `int[][]` is "an array of `int` arrays", which is how a table or grid is stored: `grid[row][column]`. Each row is its own array, and rows may even have different lengths. This is covered in the optional "grids" block of S2.

### What arrays cannot do

- **Change size.** To "add an element" you create a new, larger array and copy. The `ArrayList` we meet in S4 does this for you.
- **Hold mixed types.** An `int[]` holds only `int`.
- **Print themselves nicely.** Passing an array straight to `IO.println` prints something like `[I@2d9d4f9d` (the type and an identity number), not its contents. Use `Arrays.toString`.

## Example

```java
void main() {
    int[] scores = {70, 85, 90};
    IO.println(Arrays.toString(scores));
    IO.println(scores.length);

    IO.println(Arrays.toString(new int[3]));

    int[] unsorted = {5, 2, 9, 1};
    Arrays.sort(unsorted);
    IO.println(Arrays.toString(unsorted));

    try {
        IO.println(scores[3]);
    } catch (ArrayIndexOutOfBoundsException e) {
        IO.println("caught: " + e.getMessage());
    }
}
```

Output:

```
[70, 85, 90]
3
[0, 0, 0]
[1, 2, 5, 9]
caught: Index 3 out of bounds for length 3
```

**Going outside the array.** Valid indexes of `scores` are 0, 1 and 2. Without the `try`, reading `scores[3]` stops the program:

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3
	at Uncaught.main(Uncaught.java:3)
```

The message tells you the index you used and the length, and the second line tells you the file and line number. Negative indexes fail the same way (`Index -1 out of bounds for length 3`). Unlike in C, Java always checks, so a wrong index can never silently read or overwrite other memory. Unlike in Python, a negative index does **not** count from the end.

**An array that does not exist.** A variable can hold `null`, meaning "no array":

```java
int[] a = null;
IO.println(a.length);
```

```
Exception in thread "main" java.lang.NullPointerException: Cannot read the array length because "<local1>" is null
```

(When a program is compiled with `javac` the message names the variable, `"a"`, instead of `"<local1>"`.)

**Copying.** Assigning an array copies only the reference. To get a separate array, copy it:

```java
int[] e1 = {1, 2, 3};
int[] copy = Arrays.copyOf(e1, 5);   // [1, 2, 3, 0, 0]
int[] clone = e1.clone();            // a new array with the same elements
clone[0] = 9;                        // e1 is still [1, 2, 3]
```

**Comparing.** `==` on arrays asks "is this the same array?", not "do they contain the same values":

```java
int[] p = {1, 2, 3};
int[] q = {1, 2, 3};
IO.println(p == q);                  // false: two different arrays
IO.println(Arrays.equals(p, q));     // true: same contents
```

## Common confusions

### "The first element is number 1"

It is number 0. An array of length 3 has indexes 0, 1, 2, and `array[array.length]` is always an error.

### `length` versus `length()`

`array.length` has no parentheses; `string.length()` does. The compiler will tell you if you mix them up ("cannot find symbol").

### "I can make the array bigger"

You cannot. You create a new array and copy the old values into it, for example with `Arrays.copyOf`. In S4 you will use `ArrayList`, which does it behind the scenes.

### Printing an array shows `[I@...`

You printed the reference, not the contents. Use `Arrays.toString(a)` (or `Arrays.deepToString(a)` for arrays of arrays).

### `==` and `equals` on arrays

`==` compares whether two variables refer to the same array. `a.equals(b)` on arrays does the same. To compare contents, use `Arrays.equals(a, b)`.

### A copy that is not a copy

`int[] b = a;` makes two names for **one** array. Change one and the other changes. `clone()` and `Arrays.copyOf` copy only the first level: for an array of arrays, the inner arrays are still shared (a *shallow* copy).

### Default values and uninitialised variables

Array elements always get a default value, but an ordinary local variable does not: `int x; IO.println(x);` is a compile error. Do not rely on `0` for local variables.

> [!TIP]
> **From Python:**
> A Java array is similar to a Python list, with four differences. Its size is fixed; every element has the same type; there are no negative indexes (`a[-1]` is an error); and there is no slicing (`a[1:3]`). The growing, mixed-type, slicing structure you know is closer to `ArrayList` (S4).

> [!TIP]
> **From C#:**
> Almost the same: `int[] a = new int[3];`, `a.Length` in C# becomes `a.length` in Java (lowercase, a field). Java has no `List<T>`-style `Add` on arrays either; use `ArrayList<T>`. Multidimensional arrays use `int[][]` as in C#'s jagged arrays; there is no rectangular `int[,]`.

## Further reading

- [Java Tutorials: Arrays](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html): the official introduction.
- [Arrays class in the Java API](https://docs.oracle.com/en/java/javase/): in the API documentation, look for `java.util.Arrays`.
