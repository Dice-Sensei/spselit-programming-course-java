---
type: concept
title: Static typing
summary: Every variable has a type that the compiler checks before the program runs.
sessions: [01-kickoff]
status: draft
---

# Static typing

Every variable has a type that the compiler checks before the program runs.

## How it works

You declare a type for each variable, for example `int age`. The [compiler](compiler.md) refuses code that mixes types incorrectly, such as putting text into an `int`. Mistakes are found early, before the program starts.

### What a type is

A type says **what kind of values** a variable can hold and **what you can do with them**. An `int` holds whole numbers and supports `+`, `-`, `*`, `/`. A `String` holds text and has methods such as `length()` and `toUpperCase()`. A `boolean` holds `true` or `false`. The type tells the compiler how much memory a value needs and which operations make sense.

### "Static" means "known before running"

There are two moments at which a language can know the type of a value:

- **Static typing:** the type belongs to the *variable* (and to every expression) and is fixed in the source code. The compiler checks all of it at compile time. Java, C, C# and Kotlin work this way.
- **Dynamic typing:** the type belongs to the *value* and is checked only when the line runs. Python and JavaScript work this way.

In Java, once you write `int age = 17;`, `age` is an `int` for its entire life. It can hold different numbers, but never text.

### What the compiler checks

- **Assignments:** the value must fit the variable's type.
- **Method calls:** the method must exist for that type, and the arguments must have the right types.
- **Return values:** a method declared `int` must return an `int`.
- **Operators:** `"a" - 1` makes no sense and is rejected.
- **Initialisation:** a local variable must be given a value before it is read.

### Conversions between types

Java converts automatically when nothing can be lost, and requires you to say so when something can.

- **Widening** (safe, automatic): an `int` fits into a `long` or a `double`, so `double d = 5;` is fine.
- **Narrowing** (may lose information, explicit): a `double` does not fit into an `int`, so you must write a **cast**: `int i = (int) 3.99;` gives `3`. The fractional part is cut off.

This is also why Java makes you think about the types you use: the compiler will not guess.

### Type inference with `var`

Writing the type every time can be repetitive. Since Java 10 you may write `var` for local variables, and the compiler works out the type from the right-hand side:

```java
var count = 5;          // int
var name = "Ada";       // String
```

This is still static typing. The type is fixed at compile time and cannot change later. `var` is a shortcut for you, not a "type that can be anything".

### Why static typing

- **Early errors:** typos and wrong-type mistakes are found when you compile, not when a user hits that line.
- **Better tooling:** because the IDE knows every type, it can offer accurate code completion and rename things safely.
- **Documentation:** a signature like `double celsiusToFahrenheit(double c)` tells you what goes in and what comes out.
- **Speed:** the JVM knows the types and can generate efficient code.

The price is more to write, and a program with a type error will not run at all, even if the broken line is never reached.

## Example

```java
void main() {
    int age = 17;
    double height = 1.75;
    String name = "Ada";

    double sum = age + height;       // fine: int widens to double
    IO.println(name + " " + sum);

    int rounded = (int) height;      // narrowing needs a cast
    IO.println(rounded);
}
```

Now try breaking it, one line at a time, and read each message:

```java
int a = "seventeen";    // incompatible types: String cannot be converted to int
int b = 3.7;            // incompatible types: possible lossy conversion from double to int
age = "hello";          // the variable age is still an int
var x = 5;
x = "hi";               // var does not make x flexible: String cannot be converted to int
```

Note that the last two are checked against the *declared* type. The compiler will not let a variable change type later in the program.

## Common confusions

### Static typing versus strong typing

They are different ideas. *Static* is about **when** types are checked (before running). *Strong* is about **how strictly** types are enforced, meaning how few automatic conversions happen. Python is dynamic *and* strong (`"a" + 1` is an error). Java is static *and* strong. JavaScript is dynamic and weak (`"a" + 1` gives `"a1"`).

### "`var` means Java has dynamic typing"

It does not. `var` only saves you from writing the type; the compiler fills it in once, and it never changes.

### "I will get an error at run time"

For type mistakes, no: you get them at compile time. Run-time problems are different, such as dividing by zero or reading outside an array. Static typing does not prevent those. See [compiler](compiler.md) for compile time versus run time.

### Types of values and of variables

In Java, a variable has a type. The thing it holds also has a type, which is usually the same, but for objects can be more specific. That distinction (important for inheritance) will come in S5.

> [!TIP]
> **From Python:**
> In Python you can write `x = 5` and later `x = "five"`. In Java the first line fixes the type, so the second fails to compile. Python has *type hints* (`age: int = 17`), but the interpreter does not enforce them; in Java the compiler always does. In exchange, Java catches a whole group of mistakes before the program starts.

> [!TIP]
> **From C#:**
> Very familiar: C# is statically typed as well and has `var` with the same meaning. One difference: Java has no `dynamic` type.

## Further reading

- [Java Language Specification: Types, Values and Variables](https://docs.oracle.com/javase/specs/): the formal rules, including all conversions.
