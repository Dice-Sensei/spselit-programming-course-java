---
type: session
title: Kickoff and first programs
session: 1
status: updated
blocks:
  - {name: setup, status: done}
  - {name: first-program, status: done}
  - {name: ide, status: done}
  - {name: control-flow, status: done}
  - {name: loops, status: moved-to-02}
  - {name: methods, status: moved-to-02, optional: true}
  - {name: input, status: moved-to-02, optional: true}
concepts: [jdk, jvm, compiler, bytecode, static-typing, primitive-type, compact-source-file]
---

# S1 — Kickoff and first programs

## Goal

By the end of this session you have Java installed, you can write and run a small program with just a text editor, and you can use variables, `if`, `for` and `while`.

## Setup

Install the JDK and check that `java -version` shows 25 or higher. Follow the [setup guide](../../setup.md#2-check-your-java-version). If something goes wrong, see [common problems](../../setup.md#common-problems).

If you cannot install Java yet, you can still follow along in an online compiler such as [OneCompiler](https://onecompiler.com/java). Paste the code into the editor and press Run; nothing needs to be installed. Online compilers may run an older Java version, so the compact `void main()` and `IO.println` examples can fail there. In that case, use the traditional form, [HelloOld.java](examples/HelloOld.java), as a starting point.

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

For comparison, the same program in the traditional form, with a class and `public static void main(String[] args)`: [HelloOld.java](examples/HelloOld.java).

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

The `if`/`else` statement chooses between two branches depending on a condition. Loops are the next block.

### Exercises

**Core**

- Print a greeting based on the hour of the day (an `int`), for example "Good morning" or "Good evening".

## Loops

→ Continued in [S2](../02-methods-arrays-strings/README.md#loops)

## Methods (if time allows)

→ Continued in [S2](../02-methods-arrays-strings/README.md#methods)

## User input (if time allows)

→ Continued in [S2](../02-methods-arrays-strings/README.md#user-input-if-time-allows)

## Lecture notes

- Covered: setup, the first program without an IDE, the IDE, and `if`/`else`.
- Exercise done in class: a greeting chosen from the hour of the day (an `int`).
- `for` and `while` were not reached, so the loops block moved to S2.
- The same program was also shown in the traditional form, [HelloOld.java](examples/HelloOld.java), to compare with the compact `void main()`.
- Problems with internet connectivity caused some delays
