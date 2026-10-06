---
type: concept
title: Compact source file
summary: A Java file with a main method and no class around it, run directly with java File.java.
sessions: [01-kickoff]
status: complete
---

# Compact source file

A Java file with a main method and no class around it, run directly with java File.java.

## How it works

Since Java 25, a source file may contain methods and variables at the top level, with `void main()` as the entry point. The [compiler](compiler.md) wraps them in a class for you. This keeps small programs short, and you learn classes later.

### The problem it solves

Java has always required that all code lives in a class. For decades, the first program looked like this:

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");
    }
}
```

A beginner has to take in `public`, `class`, `static`, `void`, `String[] args` and `System.out` before understanding a single line, and each of those concepts only makes sense much later. Compact source files remove the ceremony so that the first program is what it looks like:

```java
void main() {
    IO.println("Hello, Java!");
}
```

This is not a different language. It is the same Java with less required around it.

### What the compiler does for you

When the [compiler](compiler.md) sees code outside any class, it creates a class around it. The class is named after the file, so `Hello.java` produces a class `Hello`. Everything in the file becomes a member of that class: top-level methods become methods, and top-level variables become fields. You can see this with `javap`: after `javac Hello.java`, the file `Hello.class` exists, and `javap -p Hello` shows `final class Hello` with the methods you wrote.

### What is allowed

- **A `main` method without the ceremony.** `void main()` is enough. It may also take the command-line arguments (`void main(String[] args)`), but it does not have to. The traditional `public static void main(String[] args)` still works too.
- **Top-level methods and variables.** You can write helper methods next to `main`, as in `Methods.java` in S1, and call them directly.
- **Imports.** You can write `import` lines at the top, as usual.
- **Automatic access to the core library.** The whole of the `java.base` module is imported for you. This means that `List`, `ArrayList`, `Map`, `Path` and others from `java.util`, `java.io` and `java.nio.file` can be used without any `import` line.
- **Extra classes in the same file.** You can declare further classes, records or enums below `main`. This is a gentle way to move from "just methods" to object-oriented code in S3.
- **`IO.println` and `IO.readln`.** Two small helper methods from the class `java.lang.IO`, added in Java 25 to make console programs short. They replace `System.out.println(...)` and the `Scanner` boilerplate.

### How it is run

`java Hello.java` compiles the file in memory and runs it, in one step (the *source launcher*). Nothing is left on disk afterwards.

### What it is not meant for

Compact source files are for learning and small programs. As a program grows, you will want several files, proper classes with names you choose, and packages. In January we switch to normal projects with a build tool, and in those the traditional form (an explicit class) is what you use. Nothing you learn is lost: a compact file is a normal class with part of it left out.

## Common confusions

### "So Java has no classes now"

Java is still a class-based language, and everything is still in a class. The compiler just writes the outer class for you. From S3 you will write your own.

### "Do I have to use this?"

For the autumn, yes: all our examples are compact source files. Most existing Java code, and code you will see online or in books, uses the traditional form with an explicit class. Both are valid, and you should be able to read the traditional form.

### The file name matters

The generated class is named after the file. Rename `Hello.java` to `Greeting.java` and the class is now `Greeting`. Also, a name like `my-file.java` is not a valid class name, so avoid hyphens and spaces in file names for Java programs.

### The "preview" or "unnamed class" error

If you get an error mentioning preview features, your JDK is older than 25. Check `java -version`. See the [setup guide](../setup.md#common-problems).

### `IO.println` is not in older tutorials

Most tutorials use `System.out.println`. It still works and does the same thing; `IO.println` is a shorter version that is new in Java 25.

> [!TIP]
> **From Python:**
> This is the same convenience Python has always had: a script with a function `main()` or just statements at the top, no class required. Java still needs a method called `main` (code outside methods is not allowed to be a bare statement), and variables at the top level are fields, not script-level globals.

## Further reading

- [JEP 512: Compact Source Files and Instance Main Methods](https://openjdk.org/jeps/512): the official description of the feature, with the reasoning behind it.
