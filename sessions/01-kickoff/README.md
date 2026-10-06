---
type: session
title: Kickoff and first programs
session: 1
status: skeleton
blocks:
  - {name: setup, status: planned}
  - {name: first-program, status: planned}
  - {name: ide, status: planned}
  - {name: control-flow, status: planned}
  - {name: methods, status: planned, optional: true}
  - {name: input, status: planned, optional: true}
concepts: [jdk, jvm, compiler, bytecode, static-typing, primitive-type, compact-source-file]
---

# S1 — Kickoff and first programs

## Goal

By the end of this session you have Java installed, you can write and run a small program with just a text editor, and you can use variables, `if`, `for` and `while`.

## Setup

Install the JDK and check that `java -version` shows 25 or higher. Follow the [setup guide](../../setup.md#2-check-your-java-version). If something goes wrong, see [common problems](../../setup.md#common-problems).

## First program without an IDE

You don't need an IDE to write Java. A text editor and a terminal are enough.

The [JDK](../../concepts/jdk.md) (Java Development Kit) is the set of tools for writing and running Java programs. Java source code is first translated by the [compiler](../../concepts/compiler.md) into [bytecode](../../concepts/bytecode.md), a compact instruction format. The [JVM](../../concepts/jvm.md) (Java Virtual Machine) then runs that bytecode.

Our programs are [compact source files](../../concepts/compact-source-file.md): a file with a `void main()` method and no class around it, run directly with `java Hello.java`.

Example: [Hello.java](examples/Hello.java)

```java
void main() {
    IO.println("Hello, Java!");
}
```

Run it with:

```
java Hello.java
```

Java uses [static typing](../../concepts/static-typing.md): every variable has a type, and the compiler checks the types before the program runs. The basic types such as `int`, `double`, `boolean` and `char` are [primitive types](../../concepts/primitive-type.md), which hold plain values. `String` holds text.

Example: [Types.java](examples/Types.java)

> [!TIP]
> **From Python:**
> In Python, a type mistake shows up only when that line runs. In Java, `int age = "seventeen";` is rejected before the program starts, and the error message tells you the line.

> [!TIP]
> **From C#:**
> Instead of `static void Main(string[] args)` inside a class, a compact source file just has `void main()`. The class is generated for you.

### Exercises

**Core**

- Store your name and age in variables and print a sentence using them.
- Put text into an `int` variable and read the compiler error.

**Extra**

- Print the limits of `int` and `long`, then trigger an overflow.

## Meet the IDE

Install IntelliJ IDEA, open the same `Hello.java`, and see what an IDE adds: highlighting of errors while you type, code completion, a run button and a debugger.

## Control flow

Example: [ControlFlow.java](examples/ControlFlow.java)

The `if`/`else` statement chooses between two branches, `for` repeats a fixed number of times, and `while` repeats as long as a condition is true.

### Exercises

**Core**

- FizzBuzz.
- A Celsius-to-Fahrenheit table with a `for` loop.

**Extra**

- A number-guessing game (needs the [user input](#user-input-if-time-allows) block).

**Challenge**

- All primes up to N, or a triangle drawn with `*`.

## Methods (if time allows)

Example: [Methods.java](examples/Methods.java)

A method gives a name to a piece of code that takes inputs and returns a result.

### Exercises

**Core**

- Turn the temperature conversion into its own method.

## User input (if time allows)

Example: [Input.java](examples/Input.java)

`IO.readln` reads a line of text from the keyboard. Combine it with `Integer.parseInt` to turn the text into a number.

## Lecture notes

<!-- filled after the lecture -->
