---
type: concept
title: Compiler
summary: Translates Java source code into bytecode before the program runs.
sessions: [01-kickoff]
status: complete
---

# Compiler

Translates Java source code into bytecode before the program runs.

## How it works

The compiler reads your source code, checks it for mistakes such as type errors, and produces [bytecode](bytecode.md). If there is an error, you get a message with the line number and the program does not start. When you run `java Hello.java`, the compile step happens automatically.

### What the compiler does

The Java compiler is a program called `javac`, and it is part of the [JDK](jdk.md). It works in several stages:

1. **Parsing:** reads the text and checks that it follows the grammar of Java: matching braces, semicolons, valid keywords. Errors here are *syntax errors*.
2. **Name and type checking:** finds out what every name refers to and whether the types fit together. Is there a method called `foo`? Can a `String` be stored in an `int`? Is the variable set before it is used? These are the errors you will see most often.
3. **Code generation:** writes the result as bytecode into one `.class` file per class.

If any stage finds an error, **no bytecode is produced** and the program never starts. The compiler reports as many errors as it can in one go, so fix the first one first, because later ones are often a consequence of it.

### Compile time and run time

This is the most important idea around the compiler. A mistake can be found at two different moments:

- **Compile time:** before the program runs. Syntax errors, type errors, misspelled names. The compiler stops you, and nobody ever runs the broken program.
- **Run time:** while the program runs. Division by zero, a file that does not exist, a list index that is too large. The compiler cannot know these in advance, because they depend on the data. The [JVM](jvm.md) stops the program with an *exception* and a *stack trace*.

The more mistakes are caught at compile time, the fewer surprises there are for users. This is the main benefit of [static typing](static-typing.md).

### Running the compiler yourself

Normally you just run `java Hello.java`. The launcher then compiles the file in memory and starts it, and no file is left behind. You can also run the two steps separately:

```
javac Hello.java
java Hello
```

The first command creates `Hello.class` next to the source. The second starts the JVM and loads that class (note: no `.java`, no `.class`). Separate compilation matters for larger projects, where code is compiled once and run many times, and where build tools (from January) do this for you.

### The compiler does not make code fast

`javac` keeps things simple and does few optimisations. The big performance work is done later by the [JIT compiler](jit-compiler.md) inside the JVM. You do not need to write odd code "to help the compiler".

## Example

**A syntax error.** A missing semicolon:

```java
void main() {
    IO.println("Hi")
}
```

```
Missing.java:2: error: ';' expected
    IO.println("Hi")
                    ^
1 error
```

**A type error.** Putting text into an `int`:

```java
void main() {
    int age = "seventeen";
}
```

```
Bad.java:2: error: incompatible types: String cannot be converted to int
    int age = "seventeen";
              ^
```

**A name that does not exist:**

```java
void main() {
    String s = "a";
    s.foo();
}
```

```
Meth.java:3: error: cannot find symbol
    s.foo();
     ^
  symbol:   method foo()
```

**A variable used before it has a value:**

```java
void main() {
    int x;
    IO.println(x);
}
```

```
Unin.java:3: error: variable x might not have been initialized
```

**A run-time error, which the compiler does not catch.** This compiles fine, because the compiler does not follow the value of `zero`:

```java
void main() {
    int zero = 0;
    IO.println(10 / zero);
}
```

```
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at Div.main(Div.java:3)
```

How to read a compiler message: the file name and line number come first, then the kind of problem, then the line itself with a `^` under the place where the compiler gave up. The real problem is sometimes on the *previous* line, as with the missing semicolon.

## Common confusions

### "The compiler is the same as the JVM"

No. The compiler runs **before** the program and produces bytecode. The JVM runs the bytecode **while** the program runs. With `java Hello.java` both happen in one command, which hides the difference. Compile errors contain `error:`; run-time errors start with `Exception in thread "main"`.

### "My program compiled, so it is correct"

Compiling only proves that the code is well-formed and the types fit. It says nothing about whether the program does what you want. Testing, and your own reading, do that.

### "Javac produces machine code"

It produces bytecode, which is not machine code for any real processor. Machine code is made later by the [JIT](jit-compiler.md) inside the JVM.

### The error is not where the arrow points

The compiler reports where it *noticed* the problem. A missing `}` or `)` can lead to an error many lines below it. If an error makes no sense, look at the lines just above it.

> [!TIP]
> **From Python:**
> Python also compiles source to bytecode, but it does it silently when you run the file, and it does not check types. In Python, `"a" + 1` fails only when that line runs. In Java, the equivalent is rejected before the program starts. The price: you declare types, and you cannot run a half-finished program with errors in unused corners.

> [!TIP]
> **From C#:**
> Very similar. `javac` corresponds to the C# compiler: it checks types and produces an intermediate form (bytecode in Java, CIL in C#) that a runtime executes.

## Further reading

- [Java Language Specification](https://docs.oracle.com/javase/specs/): the definition of what is valid Java, which the compiler follows.
