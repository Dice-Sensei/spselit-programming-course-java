---
type: concept
title: Reference vs value
summary: Primitives are copied when passed around; for objects and arrays the reference is copied, so both copies point to the same thing.
sessions: [02-methods-arrays-strings]
status: draft
---

# Reference vs value

Primitives are copied when passed around; for objects and arrays the reference is copied, so both copies point to the same thing.

## How it works

A Java variable holds one of two kinds of content:

- **A value:** variables of a [primitive type](primitive-type.md) (`int`, `double`, `boolean`, `char`, ...) contain the number or truth value itself.
- **A reference:** variables of every other type (arrays, `String`, and all objects) contain a *reference*, which tells the [JVM](jvm.md) where in the heap the actual array or object lives. It is something like an address, or the "remote control" for the thing.

Copying a variable always copies *what it contains*. For a primitive that gives you a second, independent number. For a reference it gives you a second reference to the **same** array or object.

### Java always copies

You will often hear "primitives are passed by value and objects are passed by reference". The second half is not accurate and leads to confusion. The precise rule is shorter:

> **Java always passes a copy of the variable's content.** For a primitive, the content is the value. For an object or array, the content is the reference.

So there is a single mechanism. The difference in behaviour comes from *what is being copied*.

### Primitives: independent copies

```java
void doubleInt(int n) {
    n = n * 2;
}
```

The parameter `n` is a separate variable that starts with a copy of the argument. Changing `n` has no effect on the caller's variable. The method can never change the caller's `int`.

### Arrays and objects: shared thing

```java
void doubleAll(int[] a) {
    for (int i = 0; i < a.length; i++) {
        a[i] = a[i] * 2;
    }
}
```

Here the parameter `a` is a copy of the *reference*. The caller's variable and `a` now point to the same array in the heap. Writing `a[i] = ...` follows the reference and changes that shared array, so the caller sees the change after the method returns.

### But the reference itself is only a copy

This is the part that "passed by reference" gets wrong. If the method assigns a **new** array to the parameter, it changes only its own copy of the reference. The caller's variable still points to the original:

```java
void replace(int[] a) {
    a = new int[]{0, 0, 0};
}
```

After `replace(nums)`, the caller's `nums` is unchanged. A method can modify *what a reference points to*, but it cannot make the caller's variable point somewhere else. (This is why you cannot write a `swap(a, b)` method that swaps two `int`s, or two array variables, in Java.)

### Aliases

Assignment also copies the reference:

```java
int[] alias = nums;
alias[0] = 99;    // nums[0] is 99 too
```

`nums` and `alias` are two names for one array. Programmers say they are *aliases*. This is the same effect as passing an array to a method, since passing a parameter is an assignment to the parameter.

### `null`

A reference variable may refer to nothing, which is written `null`. Using it (for example `a.length` when `a` is `null`) throws a `NullPointerException`. Primitive variables can never be `null`.

### `==` on references

For primitives, `==` compares the values. For references, `==` compares **whether both variables refer to the same thing**, not whether the things look equal. Two separate arrays with the same contents are not `==`. For `String`, this is why you compare text with `.equals()`, see [string](string.md).

### Immutable objects

If an object cannot be changed after it is created, then sharing it between variables is harmless: nobody can modify it behind your back. [Immutability](immutability.md) is why `String` can be shared freely. The "changes" you make to a string produce a new string and leave the old one alone.

### Where things live

Local primitive variables live in the method's frame on the stack. Arrays and other objects live in the heap, and a reference variable holds the way to find them. When no variable refers to an object any more, the garbage collector can free it (see the [JVM](jvm.md) page).

## Example

```java
void doubleInt(int n) {
    n = n * 2;
}

void doubleAll(int[] a) {
    for (int i = 0; i < a.length; i++) {
        a[i] = a[i] * 2;
    }
}

void replace(int[] a) {
    a = new int[]{0, 0, 0};
}

void main() {
    int x = 5;
    doubleInt(x);
    IO.println(x);

    int[] nums = {1, 2, 3};
    doubleAll(nums);
    IO.println(Arrays.toString(nums));

    int[] alias = nums;
    alias[0] = 99;
    IO.println(Arrays.toString(nums));

    replace(nums);
    IO.println(Arrays.toString(nums));
}
```

Output:

```
5
[2, 4, 6]
[99, 4, 6]
[99, 4, 6]
```

Read the four lines in order:

1. `5`: the `int` was copied, and `doubleInt` doubled its own copy.
2. `[2, 4, 6]`: `doubleAll` received a copy of the reference and wrote to the shared array.
3. `[99, 4, 6]`: writing through `alias` changed the array that `nums` also refers to.
4. `[99, 4, 6]`: `replace` pointed its own parameter at a new array; the caller's `nums` still refers to the old one.

**Comparing.** `==` on primitives compares values; on arrays it compares identity:

```java
int a = 5;
int b = 5;
IO.println(a == b);       // true

int[] p = {5};
int[] q = {5};
IO.println(p == q);       // false: two different arrays
int[] r = p;
IO.println(p == r);       // true: the same array
```

**Shallow copies.** Copying an array of arrays copies only the outer array. The inner arrays are still shared:

```java
int[][] grid = {{1, 2}, {3, 4}};
int[][] copy = grid.clone();
copy[0][0] = 99;
IO.println(Arrays.deepToString(grid));   // [[99, 2], [3, 4]]
```

## Common confusions

### "Arrays are passed by reference"

Close, but not accurate: Java passes a *copy of the reference*. The method can change the array's contents, but it cannot replace the caller's variable (see `replace` above). The difference is rarely visible, but it is what keeps the model consistent.

### "A method cannot change what I pass in"

True for primitives. For an array or object, a method can change its contents. When you write a method that takes an array, decide whether it is allowed to change it. If not, make a copy first.

### "Copying a variable copies the object"

Only for primitives. `int[] b = a;` is two references to one array. To copy the contents you need `a.clone()` or `Arrays.copyOf(a, a.length)`.

### "Strings behave differently from arrays, so they must be primitive"

`String` is not primitive; it is a reference type, like an array. But it is immutable, so you cannot modify it through any reference, and the shared-thing effect never shows up. See [immutability](immutability.md).

### "`null` is zero"

No. `null` means "no object". `int` cannot be `null` at all, and `0` is a valid value.

> [!TIP]
> **From Python:**
> You already know this model. In Python, *every* variable is a reference to an object, and an assignment makes another name for the same object (`b = a` for lists). The difference in Java is that **primitives are not objects**. `int x = 5;` stores the number itself, so you can never have two names referring to the same `int` object, and `x = x + 1` never changes anything else. Python's `is` corresponds to Java's `==` on references, and Python's `==` on lists corresponds to `Arrays.equals(...)`.

> [!TIP]
> **From C#:**
> The same split exists as value types (`int`, `struct`) and reference types (`class`, arrays). In C# you can pass a variable by reference with the `ref` and `out` keywords; Java has no equivalent, so a method can only return one value, and the caller's variable cannot be changed by the method.

## Further reading

- [Java Tutorials: Passing Information to a Method](https://docs.oracle.com/javase/tutorial/java/javaOO/arguments.html): the official explanation, including "Passing Reference Data Type Arguments".
