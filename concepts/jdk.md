---
type: concept
title: JDK
summary: The Java Development Kit, the tools you need to write, compile and run Java programs.
sessions: [01-kickoff]
status: complete
---

# JDK

The Java Development Kit, the tools you need to write, compile and run Java programs.

## How it works

The JDK contains the [compiler](compiler.md) (`javac`), the `java` launcher that starts the [JVM](jvm.md), and the standard library. You install it once. With a JDK you can run a source file directly using `java Hello.java`.

### What is inside

Open the folder where the JDK is installed and you will find roughly this:

| Folder | Content |
|---|---|
| `bin/` | The command-line tools (see below) |
| `lib/` | The JVM itself and supporting files |
| `jmods/` | The standard library, split into *modules* such as `java.base` |
| `conf/` | Configuration files, for example security settings |
| `include/` | Header files for calling Java from C and the other way round |
| `release` | A small text file with the exact version and the platform it was built for |

### The tools in `bin/`

You will use only a few of them in this course:

| Tool | What it does |
|---|---|
| `java` | Starts a JVM and runs a program |
| `javac` | The compiler: `.java` files into `.class` files |
| `javap` | Shows what is inside a `.class` file (see [bytecode](bytecode.md)) |
| `jshell` | An interactive prompt where you type Java and see the result immediately |
| `jar` | Packs many `.class` files into one `.jar` file (a zip) |
| `jcmd`, `jps`, `jstack` | Look inside running JVMs |
| `jlink`, `jpackage` | Build a trimmed-down runtime or an installer for your app |

### The standard library

Besides tools, the JDK includes the **Java standard library** (also called the *class library* or the *JDK API*): thousands of ready-made classes such as `String`, `ArrayList`, `Math`, `Path` and `IO`. Every Java program can use them without installing anything else. The library is divided into **modules**. The most important one is `java.base`, which holds the core packages like `java.lang` and `java.util`.

### JDK, OpenJDK and distributions

*OpenJDK* is the open-source project that produces the JDK. Several companies build it and publish installers, for example Eclipse Temurin, Amazon Corretto and Oracle. They contain the same Java, and your choice rarely matters. See [Java versions and who provides them](../setup.md#java-versions-and-who-provides-them).

### Versions

A new JDK appears every six months, and every two years one is a long-term support (LTS) release. This course uses **25**, an LTS. You can install several JDKs side by side. Which one `java` starts is decided by `PATH` (and by `JAVA_HOME` for tools that use it), see [Setting PATH and JAVA_HOME](../setup.md#setting-path-and-java_home).

## Example

Check what you have installed:

```
java -version
javac -version
```

Both should report the same version. If `java` works but `javac` is "not recognized", you probably have only a runtime and not a full JDK, or `PATH` points to a different folder.

Ask the JVM where it lives:

```java
void main() {
    IO.println("Java version: " + System.getProperty("java.version"));
    IO.println("Vendor:       " + System.getProperty("java.vendor"));
    IO.println("Installed in: " + System.getProperty("java.home"));
}
```

Try `jshell`: type `1 + 2` and press Enter, then `"Java".length()`, then `/exit` to leave. No file and no `main` are needed, which is handy for trying out small ideas.

## Common confusions

### JDK, JRE and JVM

The **JVM** runs programs. The **JDK** includes the JVM *and* the tools for writing programs. The **JRE** was an older, smaller download without the development tools; since Java 11 most distributions ship only the JDK. When someone says "install Java", they usually mean "install the JDK".

### "I have Java but `javac` doesn't work"

Some computers (especially ones with software that needs Java to run) have only a runtime. You need a full JDK. Download one as described in the [setup guide](../setup.md).

### Several versions installed

It is common to have an old Java from another class or program. The `java -version` you see is the first one found on `PATH`. If it shows an older version than expected, fix `PATH` rather than uninstalling things.

### The JDK is not an IDE

The JDK has no editor and no windows. IntelliJ IDEA, VS Code and similar tools are *IDEs*: they call the JDK's tools for you. An IDE needs a JDK to work with, but a JDK does not need an IDE.

## Further reading

- [OpenJDK](https://openjdk.org/): the project behind the JDK, with release schedules.
- [Java SE documentation](https://docs.oracle.com/en/java/javase/): the reference for the standard library; pick the Java version, then "API".
