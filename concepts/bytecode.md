---
type: concept
title: Bytecode
summary: The compact instruction format that the compiler produces and the JVM runs.
sessions: [01-kickoff]
status: draft
---

# Bytecode

The compact instruction format that the compiler produces and the JVM runs.

## How it works

Bytecode is not written for humans and not for one specific processor. The [compiler](compiler.md) produces it from your source code, and the [JVM](jvm.md) executes it. This is why one compiled Java program can run on many systems.

### Why an in-between format

A processor (Intel, AMD, Apple Silicon) understands only its own machine code, and these differ between processor families. Compiling Java straight to machine code would mean one build per processor and operating system. Instead:

- the compiler translates Java into bytecode once, for an imaginary processor that every JVM knows how to run;
- each JVM translates bytecode into the real machine code of the computer it runs on.

The "write once, run anywhere" idea of Java is built on this. Bytecode is also much simpler than Java source, which makes it easier for the JVM to check and optimise.

### The `.class` file

The compiler writes the bytecode of each class into a `.class` file. Besides instructions, the file contains the class name, field and method names and types, and a table of constants such as strings and numbers.

Every `.class` file starts with the same four bytes, `CA FE BA BE` in hexadecimal, a "magic number" that tells the JVM this is a class file. Next comes a version number that says which Java version produced the file. Class files made by Java 25 have major version 69, and the number goes up by one with each release. An older JVM refuses to load a class file from a newer Java and reports an `UnsupportedClassVersionError`.

Many `.class` files are often packed together into a single `.jar` file. A JAR is simply a zip archive.

### What instructions look like

The JVM is a *stack machine*. Instructions do not name registers; they push values onto a small stack and take them from it. To compute `a + b`, load `a` onto the stack, load `b`, then run an add instruction that takes the top two values and pushes the result. Each instruction is one byte (the *opcode*), sometimes followed by arguments. That is where the name *bytecode* comes from.

The instructions have a type in their name. `iadd` adds two `int`s, `dadd` adds two `double`s, `iload` loads an `int`. This is how bytecode keeps the types that the compiler checked, see [static typing](static-typing.md).

### What bytecode is not

Bytecode is not a hidden form of your code. It keeps names of classes, methods and fields, so it can be *decompiled* back into readable Java quite well. Tools called obfuscators exist that scramble the names. It is also not slow: the JVM compiles the hot parts to machine code while running.

## Example

Take this small program, saved as `Add.java`:

```java
int add(int a, int b) {
    return a + b;
}

void main() {
    IO.println(add(2, 3));
}
```

Compile it and look at the bytecode with the `javap` tool from the [JDK](jdk.md):

```
javac Add.java
javap -c Add
```

The part of the output for the `add` method:

```
int add(int, int);
  Code:
     0: iload_1
     1: iload_2
     2: iadd
     3: ireturn
```

Read it line by line:

| Instruction | Meaning |
|---|---|
| `iload_1` | Push the first parameter `a` (an `int`) onto the stack |
| `iload_2` | Push the second parameter `b` onto the stack |
| `iadd` | Take the top two numbers, add them, push the result |
| `ireturn` | Return the number on top of the stack |

Slot `0` is taken by `this`, which is why the parameters are numbered 1 and 2. The numbers at the start of each line are positions in bytes within the method.

You can also look at the first bytes of the file. On macOS and Linux, `xxd -l 8 Add.class` prints something like:

```
cafe babe 0000 0045
```

`cafe babe` is the magic number, and the last number is the version (hexadecimal `45` is 69, which is Java 25; a newer JDK shows a higher number).

## Common confusions

### Bytecode and machine code

Bytecode is for the JVM, which is a program. Machine code is for the processor. A Java program becomes machine code only when the [JIT compiler](jit-compiler.md) inside the JVM creates it at run time. It is never saved in your `.class` files.

### Bytecode and source code

You do not edit `.class` files by hand; you change the source and compile again. `java Hello.java` hides this by compiling in memory every time.

### Other languages use bytecode too

Kotlin, Scala and other languages compile to the same bytecode, so the JVM runs all of them.

> [!TIP]
> **From C#:**
> The counterpart is CIL (Common Intermediate Language), stored in `.dll` and `.exe` files. Like Java bytecode, it is stack-based and compiled by a JIT at run time.

## Further reading

- [The Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/): the chapter "The Java Virtual Machine Instruction Set" lists every instruction.
