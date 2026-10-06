---
type: concept
title: JVM
summary: The Java Virtual Machine, the program that runs Java bytecode.
sessions: [01-kickoff]
status: draft
---

# JVM

The Java Virtual Machine, the program that runs Java bytecode.

## How it works

The JVM reads [bytecode](bytecode.md) and executes it on your computer. The same bytecode runs on Windows, macOS and Linux, because each system has its own JVM. The `java` command starts the JVM.

### What "virtual machine" means

A *virtual machine* is a program that behaves like a computer with its own simple processor. Bytecode is the machine code of that imaginary processor. Your real processor (Intel, AMD, Apple Silicon) cannot run bytecode directly, so the JVM stands between the two.

The word "JVM" is used for three related things:

- **The specification:** a document (the *Java Virtual Machine Specification*) that says exactly what a JVM must do with bytecode.
- **An implementation:** a program that follows the specification. The one in almost every JDK is called **HotSpot**. Others exist (for example OpenJ9), but you will not meet them in this course.
- **A running instance:** every time you start `java`, the operating system starts one new process, and that process *is* a JVM. Two Java programs running at the same time are two separate JVMs.

The JVM is shipped as part of the [JDK](jdk.md). Before Java 11, there was also a smaller download called the JRE (Java Runtime Environment) with just the JVM and the standard library. Today most distributions ship only the JDK.

### What happens when you run `java Hello.java`

1. The `java` launcher starts a new operating system process and creates the JVM inside it.
2. Because you gave it a `.java` file, it first runs the [compiler](compiler.md) *in memory* (the *source launcher* mode). No `.class` file is written to disk.
3. The JVM loads the compiled class. A **class loader** finds the class and brings it into memory.
4. The **verifier** checks the bytecode: for example that it never mixes up types and never jumps to a nonsensical place. This is a safety net for bytecode that did not come from a trustworthy compiler.
5. The JVM looks for `main()` and starts executing it.
6. When `main()` and all other non-background threads finish, the JVM shuts down and the process ends.

If you compile separately with `javac Hello.java`, you get a `Hello.class` file, and you run it with `java Hello`. Steps 3 to 6 are the same.

### Interpreting and JIT compilation

The JVM executes bytecode in two ways, and uses both at once:

- **Interpreter:** reads one bytecode instruction at a time and does what it says. It starts immediately but is slow.
- **[JIT compiler](jit-compiler.md)** (*just-in-time*): the JVM watches which methods run often and translates exactly those into real machine code while the program is running. This machine code is much faster.

So Java code is compiled **twice**: by `javac` into bytecode before the program runs, and by the JIT into machine code while it runs. This is why a Java program often gets *faster* after it has been running for a few seconds. See the [JIT compiler](jit-compiler.md) page for how it works.

### Memory

The JVM manages memory for your program. The main areas are:

| Area | What lives there | Notes |
|---|---|---|
| **Stack** | One per thread. Every method call gets a *frame* with its local variables (including all primitive variables) | Small, typically about 1 MB. Frames are removed automatically when the method returns |
| **Heap** | All objects and arrays | Shared by all threads. Cleaned up by the garbage collector |
| **Metaspace** | Information about loaded classes, such as method code and field names | Grows as needed; separate from the heap |

This will matter from S2 on: when we talk about *reference vs value*, a variable holding an object does not contain the object, only a reference to it in the heap.

### Garbage collection

In Java you create objects with `new`, but you never delete them. The **garbage collector** (GC) finds objects that no part of the program can reach any more and reclaims their memory. In C you would call `free()` yourself, and forgetting it is a classic bug; Java removes that whole class of bugs. The price is that the JVM does extra work in the background, and that you do not control exactly *when* an object is removed.

Several collectors exist, with different trade-offs between throughput and pauses. The default is **G1**. Newer ones such as ZGC aim for pauses of under a millisecond, even with very large heaps.

### Other things the JVM does

- **Threads:** the JVM creates and schedules threads on top of the operating system's threads, so a Java program can do several things at once.
- **Standard library:** the thousands of ready-made classes you use (`String`, `ArrayList`, `Files`) are loaded by the JVM when needed.
- **Security and safety:** array bounds are checked, null pointers throw exceptions instead of crashing the process, and bytecode is verified. Java programs normally cannot corrupt memory the way C programs can.
- **Native code:** when needed, Java can call libraries written in C. This is the one place where "runs everywhere" stops being automatic.
- **Monitoring tools:** the JDK has tools that look inside a running JVM, such as `jcmd` and `jps` (list running JVMs).

### Other languages on the JVM

The JVM does not know or care about the Java *language*. It only runs bytecode. Any compiler that produces valid bytecode works. Kotlin, Scala, Groovy and Clojure are all languages that run on the JVM and can use Java libraries.

## Example

**1. See the stack.** Each method call needs space on the stack. A method that calls itself forever uses it all up, and the JVM stops it with a `StackOverflowError`. This is a normal Java error that you can catch (here we only do it to print a message).

```java
int depth = 0;

void recurse() {
    depth++;
    recurse();
}

void main() {
    try {
        recurse();
    } catch (StackOverflowError e) {
        IO.println("Stack overflow after about " + depth + " calls");
    }
}
```

Save it as `StackDepth.java` and run `java StackDepth.java`. The number differs between computers. Now run it with a smaller stack, using a JVM option, and watch the number drop:

```
java -Xss256k StackDepth.java
```

**2. See the heap.** The JVM can tell your program how much memory it has:

```java
void main() {
    Runtime rt = Runtime.getRuntime();
    IO.println("CPU threads: " + rt.availableProcessors());
    IO.println("Max heap:  " + rt.maxMemory() / 1024 / 1024 + " MB");
    IO.println("Used heap: " + (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024 + " MB");
}
```

Run it as `java Memory.java`, then again as `java -Xmx256m Memory.java`. By default the maximum heap is about a quarter of your computer's RAM; `-Xmx` overrides it.

**3. Watch the garbage collector.** This prints what the GC does while the program runs:

```
java -Xlog:gc Memory.java
```

**4. Look at the bytecode** the JVM runs. Compile a file and disassemble it:

```
javac Hello.java
javap -c Hello
```

Options such as `-Xss`, `-Xmx` and `-Xlog` go **before** the file name. They configure the JVM, not your program.

## Common confusions

### JVM, JDK and JRE

- The **JVM** runs bytecode.
- The **JDK** is the full toolbox: the JVM, the compiler (`javac`), the `java` launcher, `javap`, `jcmd` and the standard library.
- The **JRE** was the runtime without the development tools, and is now rarely downloaded separately.

You install the JDK, and the JVM comes with it.

### "Java is interpreted" or "Java is compiled"?

Both, and neither is the full story. `javac` compiles to bytecode, then the JVM interprets it at first and compiles the hot parts to machine code while it runs.

### "The JVM makes Java slow"

Start-up takes a moment, because the JVM has to start and warm up. Once [warmed up](jit-compiler.md#warm-up), well-written Java code is often within a small factor of C, and sometimes faster, because the JIT optimises using information that only exists at run time.

### "Write once, run anywhere" is not magic

The *bytecode* is the same everywhere, but every operating system and processor type needs its own *JVM*. This is why the setup guide asks you to pick the right installer for your system, and why Apple Silicon and Intel Macs need different downloads.

> [!TIP]
> **From Python:**
> CPython also compiles your source to bytecode (the `.pyc` files in `__pycache__`) and then interprets it with a virtual machine, so the basic idea is the same. The differences: Java checks types when it compiles, and the JVM adds a [JIT compiler](jit-compiler.md) that turns hot code into machine code. Python's default interpreter has traditionally not done this. Both languages have automatic memory management.

> [!TIP]
> **From C#:**
> The JVM is the counterpart of .NET's CLR. Bytecode corresponds to CIL, and both runtimes use a JIT compiler and a garbage collector. A C# project compiles into an `.exe` or `.dll` that holds CIL; a Java project compiles into `.class` files (or a `.jar`, which is a zip of them) that hold bytecode.

## Further reading

- [The Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/) (choose the Java SE version, then "JVMS"): the precise rules. Dense, but readable in parts.
- [OpenJDK HotSpot group](https://openjdk.org/groups/hotspot/): the project behind the default JVM.
