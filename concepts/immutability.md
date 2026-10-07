---
type: concept
title: Immutability
summary: An immutable value cannot be changed after it is created; operations on it produce a new value.
sessions: [02-methods-arrays-strings]
status: draft
---

# Immutability

An immutable value cannot be changed after it is created; operations on it produce a new value.

## How it works

Something is *immutable* if it cannot be modified after it has been created. The opposite is *mutable*. In Java, the best-known immutable object is [`String`](string.md): after `String s = "java";`, there is no way to change the characters inside that string. An [array](array.md), on the other hand, is mutable: `a[0] = 5;` changes the array itself.

So what happens when you write `s.toUpperCase()`? It does not change `s`. It creates a **new** string `"JAVA"` and returns it. The old string is still there, unchanged, until nothing refers to it any more.

```java
String s = "java";
s.toUpperCase();         // returns "JAVA", but nobody keeps it
IO.println(s);           // java
s = s.toUpperCase();     // now s refers to the new string
IO.println(s);           // JAVA
```

The last line is the important pattern: you cannot modify the string, so you change **which string the variable refers to**.

### Variable versus object

Two different things can be "changed":

1. **The variable:** you can point it at another object (`s = "other";`). This is allowed, unless the variable is declared `final`.
2. **The object itself:** its internal contents. For a `String` this is impossible; for an array it is possible.

Immutability is about the second one. `s = s.toUpperCase()` looks like a change, but it only moves the variable to a different string. Another variable that referred to the original is not affected:

```java
String a = "java";
String b = a;               // both refer to the same string
a = a.toUpperCase();        // a now refers to a new string
// b is still "java"
```

### Why make things immutable?

- **Safe sharing.** With [reference vs value](reference-vs-value.md), two variables may refer to the same object. If the object can be changed, a change through one variable surprises the other. If it cannot, sharing is harmless, so the JVM can reuse one string in many places (the string pool).
- **Fewer bugs.** An immutable object cannot be left half-changed, or be changed by a different part of the program (or a different thread) while you rely on it.
- **Safe as keys.** Objects used as keys in collections (S4) must not change while stored. Strings never do.
- **Easier to reason about.** If you pass a string to a method, you know that you will get the same string back.

### The cost

Every "change" creates a new object. Joining strings in a long loop creates many temporary strings:

```java
String result = "";
for (int i = 0; i < 100000; i++) {
    result += i;     // a new string every time
}
```

This works, but it is slow, because each step copies everything built so far. Java has a separate, mutable tool for building long texts, which you will not need in the autumn.

### Other immutable things

- The **wrapper classes** `Integer`, `Double`, `Boolean`, ... (S4).
- **Records** (S4) are shallowly immutable by design.
- **Primitive values** are not objects, but they behave like immutable values: you can store a different number in a variable, but you cannot change "the number 5".
- You can also write your own immutable classes: make all fields `final` and give them no setters (S3 and later).

### `final` is not immutability

The keyword `final` on a variable means the *variable* cannot be pointed somewhere else. It does not make the object immutable:

```java
final int[] data = {1, 2, 3};
data[0] = 99;              // allowed: the array is mutable
// data = new int[5];      // error: the variable is final
```

## Example

```java
void main() {
    String s = "java";
    s.toUpperCase();
    IO.println(s);
    s = s.toUpperCase();
    IO.println(s);
}
```

Output:

```
java
JAVA
```

The first `println` shows that the call on its own did not change `s`. The second shows what happens once the result is assigned.

**Mutable versus immutable, side by side.** A method that modifies its array parameter changes the caller's array. A method that tries to "modify" its string parameter cannot:

```java
void shout(String text) {
    text = text.toUpperCase();      // only the local variable changes
}

void doubleAll(int[] a) {
    for (int i = 0; i < a.length; i++) {
        a[i] = a[i] * 2;
    }
}
```

After `shout(word)` the caller's `word` is the same as before. After `doubleAll(nums)` the caller's `nums` has doubled values. To get a changed string out of a method, it has to **return** it:

```java
String shout(String text) {
    return text.toUpperCase();
}
```

## Common confusions

### "I called a method on the string and nothing happened"

The method returned a new string, which you did not use. Assign it: `s = s.trim();`.

### "If strings cannot change, how does `s = s + "!"` work?"

It builds a new string `s + "!"` and then makes `s` refer to it. The old string is not modified. The variable is changed, the object is not.

### "Immutable means a constant"

No. A constant is a variable that cannot be reassigned (`final`). An immutable object cannot be modified, but a variable that refers to it can still be pointed at other objects.

### "Immutable objects are slow"

Not usually. Creating small objects is cheap in Java, and the JVM can share and optimise immutable ones. The only common problem is building a long text piece by piece, which has its own tool.

> [!TIP]
> **From Python:**
> The same idea. In Python, `str`, `int` and `tuple` are immutable and `list` and `dict` are mutable, and `s.upper()` also returns a new string. In Java, the mutable counterpart of a tuple-like fixed array is the array, and the mutable list is `ArrayList` (S4).

> [!TIP]
> **From C#:**
> Also the same: `string` is immutable in C#, and `s.ToUpper()` returns a new string.

## Further reading

- [Java Tutorials: Immutable Objects](https://docs.oracle.com/javase/tutorial/essential/concurrency/immutable.html): a guide to what makes an object immutable and how to write one.
