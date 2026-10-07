---
type: concept
title: String
summary: An immutable sequence of characters, and an object with methods.
sessions: [02-methods-arrays-strings]
status: draft
---

# String

An immutable sequence of characters, and an object with methods.

## How it works

A `String` holds text: `"Hello, Java"`. You write a string literal between double quotes. Unlike `int` or `double`, `String` is **not** a [primitive type](primitive-type.md). It is a *class*, and a `String` value is an **object**. That means a `String` variable holds a [reference](reference-vs-value.md) to the text, and that you can call *methods* on it with a dot: `s.length()`, `s.toUpperCase()`. What exactly an object is, and how you create your own kinds of objects, is the topic of S3. For now, think of a string as a value that comes with a set of useful operations.

The most important fact about strings is that they are [immutable](immutability.md): once created, the characters in a string never change. Every method that seems to modify a string actually returns a **new** string.

### Characters and indexes

A string is a sequence of characters, and like an [array](array.md) it is indexed from **0**. For `String s = "Java"`:

| Index | 0 | 1 | 2 | 3 |
|---|---|---|---|---|
| Character | `J` | `a` | `v` | `a` |

`s.length()` is `4` (with parentheses, unlike `array.length`), and the last valid index is `s.length() - 1`.

The type of a single character is `char`, written in single quotes: `'J'`. A `String` is written in double quotes, even with one letter: `"J"` is a string and `'J'` is a `char`. Technically, the text is stored as UTF-16 code units, which means that the length of a string counts those units, and a few rare characters such as emoji take two of them (`"😀".length()` is `2`).

### The methods you will use most

| Call | Returns | Example with `s = "Hello, Java"` |
|---|---|---|
| `s.length()` | number of characters | `11` |
| `s.charAt(i)` | the `char` at index `i` | `s.charAt(0)` is `'H'` |
| `s.substring(from, to)` | the part from `from` up to but **not including** `to` | `s.substring(7, 11)` is `"Java"` |
| `s.substring(from)` | from `from` to the end | `s.substring(7)` is `"Java"` |
| `s.indexOf(x)` | index of the first match of a char or string, or `-1` if there is none | `s.indexOf("Java")` is `7`; `s.indexOf('z')` is `-1` |
| `s.contains(x)` | `true` if `x` occurs anywhere | `s.contains("lo")` is `true` |
| `s.startsWith(x)`, `s.endsWith(x)` | whether it begins or ends with `x` | `s.startsWith("He")` is `true` |
| `s.toUpperCase()`, `s.toLowerCase()` | a converted copy | `"HELLO, JAVA"` |
| `s.replace(a, b)` | a copy with every `a` replaced by `b` | `s.replace('a', 'o')` is `"Hello, Jovo"` |
| `s.strip()` | a copy without whitespace at both ends | `"  pad ".strip()` is `"pad"` |
| `s.isEmpty()`, `s.isBlank()` | no characters; or only whitespace | `"".isEmpty()` is `true` |
| `s.repeat(n)` | the string `n` times | `"ab".repeat(3)` is `"ababab"` |
| `s.toCharArray()` | a new `char[]` with the characters | for looping over letters |
| `s.equals(t)`, `s.equalsIgnoreCase(t)` | whether the text is the same | see below |
| `s.compareTo(t)` | negative, zero or positive, by alphabetical order | `"apple".compareTo("banana")` is negative |
| `String.valueOf(x)` | a string from any value | `String.valueOf(42)` is `"42"` |
| `String.join(sep, a, b, ...)` | the parts joined with the separator | `String.join("-", "a", "b")` is `"a-b"` |

### Joining strings with `+`

The `+` operator joins strings, and if one side is a string, the other is converted to text:

```java
"Hello, " + name + "!"
"a" + 1 + 2     // "a12"
1 + 2 + "a"     // "3a"
```

The last two lines show that `+` is evaluated from left to right: `1 + 2` is first added as numbers, and `"a" + 1` is first joined as text. Each `+` creates a new string, which is fine for a few joins.

### Comparing strings: `==` versus `equals`

Because a `String` variable holds a reference, `==` asks whether two variables refer to **the same string object**. It does *not* compare the characters. To compare the text, always use `.equals(...)`:

```java
String a = "hi";
String b = new String("hi");
a == b           // false: two different objects
a.equals(b)      // true: same characters
```

This is confusing because `==` sometimes appears to work. Identical string literals in the source code are stored once and shared (the JVM keeps a *string pool* of them), so `"hi" == "hi"` is `true`. But a string built while the program runs, for example by `x + "i"`, is a new object, and `==` gives `false` for it. You cannot rely on `==` for strings, so never use it for them.

The `equals` method is also what you use to check against a fixed value. If a variable might be `null`, write the literal first: `"yes".equals(answer)` is safe, while `answer.equals("yes")` throws a `NullPointerException` when `answer` is `null`.

### Working with characters

A `char` is also a number (its code), and you can calculate with it:

```java
char c = 'c';
(char) (c + 1)     // 'd'
c - 'a'            // 2: the position of the letter in the alphabet
(int) c            // 99
```

The `Character` class has helpers such as `Character.isLetter(c)`, `Character.isUpperCase(c)` and `Character.toUpperCase(c)`. Together with a loop over `charAt(i)`, this is enough to count vowels, check a palindrome or shift letters for a cipher.

### Strings in the JVM

String literals and strings created at run time live on the heap like any other object (see the [JVM](jvm.md) page). Since a string cannot change, the JVM can safely share one string between many variables, and cache its hash code, which makes strings fast to use as keys in collections (S4).

## Example

```java
void main() {
    String s = "Java";
    IO.println(s.length());
    IO.println(s.charAt(0));
    IO.println(s.substring(1, 3));
    IO.println(s.indexOf('v'));
    IO.println(s.toUpperCase());
    IO.println(s.toCharArray().length);
}
```

Output:

```
4
J
av
2
JAVA
4
```

`substring(1, 3)` gives the characters at index 1 and 2, but not 3, so it prints `av`.

**Comparing strings:**

```java
void main() {
    String a = "hi";
    String b = new String("hi");
    IO.println(a == b);
    IO.println(a.equals(b));
    IO.println(a.equalsIgnoreCase("HI"));
}
```

```
false
true
true
```

**Strings are immutable**, so the result of a method must be assigned to be used:

```java
String s = "java";
s.toUpperCase();              // the result is thrown away
IO.println(s);                // java
s = s.toUpperCase();
IO.println(s);                // JAVA
```

**Going outside the string.** The same kind of error as with arrays:

```java
String s = "Hello, Java";
s.charAt(20);
```

```
Exception in thread "main" java.lang.StringIndexOutOfBoundsException: Index 20 out of bounds for length 11
```

(The exact wording of the message differs a little between Java versions.)

**Looping over the characters** with an indexed loop:

```java
String word = "banana";
int count = 0;
for (int i = 0; i < word.length(); i++) {
    if (word.charAt(i) == 'a') {
        count++;
    }
}
IO.println(count);     // 3
```

Compare `char`s with `==`: for a `char`, which is a primitive, that is correct.

**Changing a character** means building a new string, usually via a `char` array:

```java
char[] letters = "abc".toCharArray();
letters[0] = 'X';
String changed = new String(letters);     // "Xbc"
```

## Common confusions

### `==` seems to work for strings

It works when both sides are the same shared literal, and that is exactly why the bug survives testing. Use `.equals` for text, every time. `==` is correct for `char`, `int`, and the other primitives.

### Calling a method and ignoring the result

`s.toUpperCase();` on its own line does nothing useful: the method returns a new string, and the original `s` is unchanged. Write `s = s.toUpperCase();` or use the returned value.

### `length` versus `length()`

Strings use `length()` with parentheses; arrays use `length` without. Mixing them up is a compile error ("cannot find symbol").

### `'a'` versus `"a"`

Single quotes are a `char` (a number); double quotes are a `String` (an object). You cannot use one where the other is expected, and `'a' + 'b'` adds two numbers (`195`), not two letters.

### `substring` end index

The second argument is exclusive: `"Java".substring(1, 3)` is `"av"`, and the number of characters you get is `to - from`.

### Joining strings in a long loop

Because every `+` creates a new string, building a very long text piece by piece is slow. Java has a class called `StringBuilder` for that job; we do not need it yet.

### Empty string, blank string and `null`

`""` is a string with no characters; `"  "` is not empty but is blank; `null` is no string at all. Calling a method on `null` throws a `NullPointerException`.

### "Strings are primitive because they are so basic"

`String` has a special place in the language (literals with double quotes, and `+`), but it is a normal class. This is why it starts with a capital letter and has methods.

> [!TIP]
> **From Python:**
> Python strings are also immutable, indexed from 0, and have similar methods (`upper()` → `toUpperCase()`, `find()` → `indexOf()`). Differences: Java has no slicing (`s[1:3]` → `s.substring(1, 3)`) and no negative indexes; single and double quotes are *not* interchangeable (`'a'` is a `char`); and **`==` does not compare text**. In Python `==` compares values and `is` compares identity; in Java, `.equals()` compares values and `==` compares identity.

> [!TIP]
> **From C#:**
> Also immutable, with very similar methods (`ToUpper()` → `toUpperCase()`, `IndexOf` → `indexOf`; method names start with a lowercase letter in Java). The big trap: in C#, `==` on strings compares the text, because the operator is overloaded. In Java, `==` on strings compares references, so you must use `.equals()`.

## Further reading

- [Java Tutorials: Strings](https://docs.oracle.com/javase/tutorial/java/data/strings.html): the official introduction.
- [String class in the Java API](https://docs.oracle.com/en/java/javase/): in the API documentation, look for `java.lang.String`; it lists every method.
