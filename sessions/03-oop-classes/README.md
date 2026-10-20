---
type: session
title: OOP and classes
session: 3
status: skeleton
blocks:
  - {name: arrays, status: planned}
  - {name: formatting, status: planned, optional: true}
  - {name: grids, status: planned, optional: true}
  - {name: objects, status: planned}
  - {name: classes, status: planned}
  - {name: encapsulation, status: planned}
  - {name: oop-map, status: planned}
  - {name: tostring-equals, status: planned, optional: true}
  - {name: debugger, status: planned, optional: true}
concepts: [array, object, class, constructor, encapsulation, access-modifier, inheritance, polymorphism, abstraction]
---

# S3 — OOP and classes

## Goal

By the end of this session you can write a small class with private fields, a constructor and a method, create objects from it, and explain why the fields are private. You also know what *object-oriented* means well enough to recognize the four big words: encapsulation, inheritance, polymorphism and abstraction.

> [!NOTE]
> This page builds on the idea of [reference vs value](../../concepts/reference-vs-value.md) from S2; read that page first if you missed the session. You need JDK 25 or newer; if `java Hello.java` fails with an error mentioning "preview" or "unnamed class", see the setup guide's [common problems](../../setup.md#common-problems).

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

> [!TIP]
> **From Python:**
> A Java array looks like a Python list but has a fixed size, one element type and no negative indexes or slicing. In Python every variable is a reference; in Java only the non-primitive ones are.

### Exercises

**Core**

- Write `int sum(int[] numbers)` and `int max(int[] numbers)`, and test them on a few arrays.

**Extra**

- Array statistics: write methods for the minimum, maximum, mean and median of an array of numbers.
- Reverse an array in place.

**Challenge**

- Rotate an array by *k* positions in place, without creating a second array.

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

## Objects: state plus behavior

You have already been using objects. In `"hello".toUpperCase()` the text `"hello"` is a `String` object, and `toUpperCase()` asks it to do something. An [object](../../concepts/object.md) bundles two things: *state* (the data it holds) and *behavior* (the operations you can ask it to do).

Why bother? Look at a bank balance without objects: the number is one variable, `deposit` is a method somewhere else, and nothing says they belong together. With objects, `ada.deposit(50)` reads like the problem it solves, and each account carries its own balance.

Examples: [StringAsObject.java](examples/StringAsObject.java) (calls methods on a string and prints its class) and [Bank.java](examples/Bank.java) (the same problem solved twice: with loose variables, then with an `Account` object).

> [!TIP]
> **From Python:**
> In Python every value is an object. In Java the primitive types (`int`, `double`, `boolean`, `char`) are not, so `5.toString()` is not valid.

### Exercises

**Core**

- Call five different `String` methods on a string you choose, and write down for each one whether it changes the string or gives back a new value.

## Classes, fields and constructors

A [class](../../concepts/class.md) is the blueprint for objects: it lists the fields (the state) and methods (the behavior) that every object of that kind has. `new` builds an object from it. Here is a die:

```java
class Die {
    int sides = 6;
    int value;

    void roll() {
        value = (int) (Math.random() * sides) + 1;
    }
}
```

Each `Die` has its own `sides` and `value`; `roll()` works on the die you call it on. `Die d = new Die(); d.roll();` makes one and rolls it. Examples: [Die.java](examples/Die.java) and [DieOld.java](examples/DieOld.java).

> [!NOTE]
> **Java 25 and classic style.** `Die.java` and `DieOld.java` are the same class in two styles. The class itself is identical; what differs is the file around it: `void main()` and `IO.println` against `public static void main(String[] args)` and `System.out.println`. The classic file also runs on older JDKs. Open the two files next to each other.

### Constructors

A [constructor](../../concepts/constructor.md) is a special method that runs during `new` and sets the object up. It has the class's name and no return type. Inside it, `this` means the object being created, so `this.sides = sides;` assigns the parameter to the field.

```java
Die(int sides) {
    this.sides = sides;
}
```

Once you write any constructor, the automatic parameter-less one is gone: `new Die()` no longer compiles, and every die must say how many sides it has. Example: [DieConstructor.java](examples/DieConstructor.java).

### Two names for one object

A variable of a class type holds a *reference*, exactly like an array variable. `Die c = a;` copies the reference, so `c` and `a` are two names for one die. Rolling `a` changes what `c` shows, and `a == c` is `true`, while two separately created dice are not `==`. This is [reference vs value](../../concepts/reference-vs-value.md) again, now with your own objects. Examples: [TwoDice.java](examples/TwoDice.java) and [TwoDiceOld.java](examples/TwoDiceOld.java).

> [!NOTE]
> **Java 25 and classic style.** `TwoDiceOld.java` has to declare `Die` as a `static` nested class, because `main` is static. Remove `static` and the classic file fails with this message:
>
> `error: non-static variable this cannot be referenced from a static context`
>
> In the Java 25 file, `main` is not static, so the problem does not arise.

### The running example: a card

Over the next sessions we build a small card game. Its first class is `Card`, a suit and a rank with a method that describes itself. Example: [Card.java](examples/Card.java). It is the starter class for S4.

### Exercises

**Core**

- Write a `Die` class with a `roll()` method and roll it five times, printing each result.
- Write a `Card` class with a suit, a rank and a `describe()` method, and print two different cards.

**Extra**

- Write a `Dice` pair (two `Die` objects) that reports whether a roll is a double.

**Challenge**

- Make a die that refuses to be rolled twice in a row without a `reset()` in between.

## Encapsulation and access modifiers

Open fields have a problem. [PublicFields.java](examples/PublicFields.java) sets `d.value = 99` on a six-sided die, and nothing stops it. The class cannot keep its own promise ("a die shows 1 to `sides`").

[Encapsulation](../../concepts/encapsulation.md) is the fix: make the fields `private`, and give outsiders methods for what they may do. Only code inside the class can then change `value`, and it does so through `roll()`. `private` and `public` are [access modifiers](../../concepts/access-modifier.md): they say who may use a field or method.

Outside code that touches a private field does not compile. [PrivateAccess.java](examples/PrivateAccess.java) fails on purpose with:

```
error: value has private access in Die
```

[Encapsulated.java](examples/Encapsulated.java) is the finished die: private fields, a getter for each value you may read, and a constructor that refuses fewer than two sides. (It prints a message and uses 6 instead; real error handling with `throw` comes in S7.) [AccessModifiers.java](examples/AccessModifiers.java) shows a private helper method. `protected` and package access are explained in S5.

> [!NOTE]
> **Why these four files use the classic layout.** In a compact source file the classes are members of the file's hidden class, and they can see each other's `private` members. We tested it: `d.value = 99` on a private field compiles and runs there. In the classic layout (top-level classes plus `public class Name { public static void main... }`) the compiler rejects it. So the encapsulation examples use the classic layout, and this is a real difference between the two styles.

> [!TIP]
> **From C#:**
> Java has no `{ get; set; }` properties. A getter is an ordinary method: `getValue()`.

### Exercises

**Core**

- Write an `Account` class with a private `balance`, a `deposit(int amount)` method that ignores negative amounts, and a `getBalance()` method.

**Extra**

- Add a `withdraw(int amount)` method that cannot overdraw the account.

**Challenge**

- Write a `Rational` class (a fraction) that always keeps itself reduced, so `2/4` is stored as `1/2`.

## The OOP map

Object-oriented programming rests on four ideas. You have just met the first one; the others get a name now and a whole session later. Each snippet below is an illustration, not code to type in yet.

- **[Encapsulation](../../concepts/encapsulation.md):** an object keeps its state private and offers methods. (This session.)
- **[Inheritance](../../concepts/inheritance.md):** a class reuses and extends another class. In S5. Illustration: `class Dog extends Animal { ... }` is an `Animal` with extra things a dog does.
- **[Polymorphism](../../concepts/polymorphism.md):** one call does different things depending on the object's actual class. In S5. Illustration: `shape.area()` runs the circle's formula for a circle and the rectangle's for a rectangle.
- **[Abstraction](../../concepts/abstraction.md):** you use something through what it does, not how. In S5 and S6. Illustration: you call `die.roll()` without knowing where the number comes from.

## toString and equals (if time allows)

Printing a `Card` that has no `toString` gives something like `ToStringDemo$PlainCard@1b68b9a4`, which is the class name and an internal number. Add a method `public String toString()` and printing uses it. The `public` is required, because `toString` already exists in every class with that access. Example: [ToStringDemo.java](examples/ToStringDemo.java).

`==` on objects asks "are these the *same object*?", exactly as for strings (see [string](../../concepts/string.md) and [reference vs value](../../concepts/reference-vs-value.md)). To ask "do they have the same content?" a class writes its own `equals` method. Example: [EqualsDemo.java](examples/EqualsDemo.java) and [EqualsDemoOld.java](examples/EqualsDemoOld.java).

> [!NOTE]
> **Java 25 and classic style.** Only one line differs between `EqualsDemo.java` and `EqualsDemoOld.java`: `if (!(o instanceof Card other))` declares the variable in the test, while the classic file tests with `instanceof Card` and then casts: `Card other = (Card) o;`.

A class that overrides `equals` should also override `hashCode`; that comes with `HashMap` in S4.

### Exercises

**Core**

- Override `toString` and `equals` on your `Card` class.

**Extra**

- Create five cards in an array and print them all.

**Challenge**

- Write a `Money` class (an amount and a currency) where `equals` treats `5 EUR` and `5 EUR` as equal, but not `5 EUR` and `5 USD`.

## The debugger (if time allows)

A debugger lets you stop a program on a line and look inside it. In IntelliJ IDEA, the lecture shows: setting a **breakpoint** by clicking next to a line number, running with the debug button, **stepping** line by line and **into** a method, and reading the **Variables** pane to watch the fields of a `Die` change as `roll()` runs. The demo file is [DebugMe.java](examples/DebugMe.java).

This is a demo and has no exercises, and nothing later depends on it. You can follow every other block with an editor and a terminal.

## Lecture notes

<!-- filled after the lecture -->
