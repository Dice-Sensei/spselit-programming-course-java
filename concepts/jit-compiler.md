---
type: concept
title: JIT compiler
summary: The part of the JVM that turns frequently used bytecode into machine code while the program is running.
sessions: [01-kickoff]
status: draft
---

# JIT compiler

The part of the JVM that turns frequently used bytecode into machine code while the program is running.

## How it works

JIT stands for *just-in-time*: the machine code is made at the last moment, while the program runs, instead of in advance. The [JVM](jvm.md) starts by interpreting [bytecode](bytecode.md), watches which parts run often, and compiles exactly those parts into machine code for your processor.

### Why it exists

There are two simple ways to run a program, and both have a weakness:

- **Interpreting** bytecode instruction by instruction starts instantly and works on every processor, but it is slow, because every instruction has to be looked up and carried out by another program.
- **Compiling everything to machine code in advance** is fast to run, but you need one build per processor and operating system, and it takes time before the program starts.

The JIT combines the two. Start interpreting at once, and spend compile time only on the code that is worth it. Most programs spend nearly all their time in a small part of their code (a loop, a frequently called method), so compiling just that part gives most of the speed.

### Two compilers in one

Java code is compiled **twice** in total:

1. Before the program runs, the [compiler](compiler.md) (`javac`) turns source code into bytecode.
2. While the program runs, the JIT turns hot bytecode into machine code.

These are two different programs with different jobs. `javac` checks your code and does little optimisation. The JIT does the heavy optimising.

### Tiers

HotSpot, the JVM in your JDK, has two JIT compilers working together, and the JVM moves code through levels:

| Level | What runs the code | Notes |
|---|---|---|
| 0 | Interpreter | Slow, collects statistics about which code is used |
| 1–3 | **C1** compiler | Compiles quickly, produces reasonably fast code, and keeps collecting statistics |
| 4 | **C2** compiler | Compiles slowly, produces the fastest code, using what was learned |

This is called *tiered compilation*. A method called a few thousand times gets compiled by C1. If it keeps being busy, it is later recompiled by C2. Compilation happens on background threads, so your program keeps running while it happens.

### What makes JIT code fast

The JIT compiler can see how the program *actually* behaves, which `javac` cannot. This allows several optimisations:

- **Inlining:** replacing a call to a small method with the method's body, so the cost of calling disappears. This is the most important optimisation, and it makes small methods practically free.
- **Dead code removal:** code that can never matter is dropped.
- **Loop optimisations:** loops get unrolled, and checks inside them (such as array bounds) are removed when they can be proven unnecessary.
- **Speculation:** the JIT bets on what it has seen. If a variable has always been of a certain type, it compiles code that assumes so, and puts in a quick guard. If the bet is lost, the JVM throws away the compiled code and goes back to the interpreter. This is called *deoptimisation* and it is invisible to your program.
- **Escape analysis:** if an object is never seen outside one method, the JIT may avoid creating it on the heap at all.

### Warm-up

Because the JIT needs time to observe and compile, Java programs are slowest at the very start and get faster in the first seconds. This is called *warm-up*. It is a normal part of the JVM, not a sign that something is wrong.

Warm-up matters in two situations. First, a very short program (like most of our examples) finishes before the JIT has much to do, so it mostly runs in the interpreter. Second, if you measure the speed of your code, measuring one single run tells you very little. A fair measurement runs the code many times first and measures after that.

### Name: HotSpot

The *hot spots* of a program are the parts that are executed most. The default JVM is called **HotSpot** because finding and compiling these is what it was designed around.

## Example

This program times the same loop eight times in a row. Save it as `Warmup.java`:

```java
long work() {
    long sum = 0;
    for (int i = 0; i < 2_000_000; i++) {
        sum += i % 7;
    }
    return sum;
}

void main() {
    for (int round = 1; round <= 8; round++) {
        long start = System.nanoTime();
        work();
        long micros = (System.nanoTime() - start) / 1000;
        IO.println("round " + round + ": " + micros + " microseconds");
    }
}
```

Run `java Warmup.java`. The times differ between computers, but the pattern is the same: the first rounds are slow, then the times drop and stay low. One run on a laptop looked like this:

```
round 1: 4673 microseconds
round 2: 3454 microseconds
round 3: 1990 microseconds
round 4: 1774 microseconds
round 5: 1716 microseconds
...
```

Now turn the JIT off with a JVM option that makes the JVM only interpret:

```
java -Xint Warmup.java
```

On the same laptop, every round took about 10,500 microseconds, and nothing improved: about six times slower than the warmed-up result.

You can also watch the JIT work. This prints a line each time a method is compiled; filter it for our method name:

```
java -XX:+PrintCompilation Warmup.java
```

Lines with `Warmup::work` show the method being compiled at tier 3 (C1) and then at tier 4 (C2), and the earlier versions being marked `made not entrant` once a better one replaces them. You do not need to understand each column.

## Common confusions

### "Java is interpreted" or "Java is compiled"?

Both. See the [JVM](jvm.md) page. Bytecode is interpreted first, and hot parts are compiled to machine code by the JIT.

### "Why is my program slow when I start it?"

Start-up is the JVM loading classes and warming up. For long-running programs this cost is paid once and does not matter. For tiny programs, it can be most of the run time.

### JIT versus `javac`

`javac` runs **before** the program, translates source to bytecode, and is part of your build. The JIT runs **inside** the JVM, **during** the program, and translates bytecode to machine code. You never run the JIT yourself.

### "Can I make the JIT compile my code?"

No, and there is no need. The JVM decides what is worth compiling. Writing normal, clear code is the best way to get good JIT results. Odd tricks that "help the JIT" usually do nothing, or make it worse.

### The machine code is not saved

The compiled machine code lives only in memory while the program runs. The next run starts from the interpreter again and repeats the warm-up. (Newer JDKs have features that reduce start-up time by caching some of this work, but that is advanced.)

> [!TIP]
> **From Python:**
> The standard Python interpreter (CPython) has traditionally no JIT: it interprets bytecode all the time, which is one reason a plain loop in Python is much slower than in Java. Recent versions of CPython include an experimental JIT, and a separate implementation called PyPy has had a JIT for years. In Java, the JIT is a normal, always-on part of the runtime.

> [!TIP]
> **From C#:**
> Same concept. The .NET runtime (CLR) also compiles CIL to machine code just in time, and it also has tiers. A difference: .NET offers *ahead-of-time* compilation for ordinary applications, while in Java it is a specialised option.

## Further reading

- [OpenJDK HotSpot group](https://openjdk.org/groups/hotspot/): the project behind the default JVM.
