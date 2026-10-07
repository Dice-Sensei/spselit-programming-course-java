---
type: concept
title: Overloading
summary: Having several methods with the same name but different parameter types or counts.
sessions: [02-methods-arrays-strings]
status: draft
---

# Overloading

Having several methods with the same name but different parameter types or counts.

## How it works

Overloading means that you can define several methods with the same name, as long as their [signatures](method-signature.md) differ, which means the parameter list has a different number of parameters or different types. When you call the method, the compiler looks at the arguments and picks the version that fits.

```java
int max(int a, int b) { ... }
double max(double a, double b) { ... }
int max(int a, int b, int c) { ... }
```

These are three different methods that happen to share a name. This is useful when the same *idea* applies to different kinds of input: "the larger of two numbers" is the same idea for `int` and for `double`.

### The compiler chooses, not the program

Which version runs is decided **at compile time**, from the types of the arguments you wrote. It is not decided while the program runs. That is why the choice depends on the *declared* types of the arguments.

### How the compiler picks

When several versions could accept a call, the compiler works in phases and uses the first phase that finds a match:

1. **Exact match, or widening of a primitive.** The argument type is the parameter type, or can be widened to it without a cast: `byte` → `short` → `int` → `long` → `float` → `double`, and `char` → `int`. If several versions match this way, the *most specific* one wins: the one whose parameter types are the narrowest. For a call with an `int`, `print(int)` is better than `print(long)`, which is better than `print(double)`.
2. **With boxing:** conversions between primitives and their wrapper classes, such as `int` and `Integer` (S4).
3. **With a variable number of arguments** (`int...`), which we do not cover.

If the compiler finds no match, or finds two that are equally good, it reports an error instead of guessing.

### What counts as different

Versions must differ in the **number**, **types** or **order** of their parameters:

- `show(int)` and `show(String)`: different types, fine.
- `max(int, int)` and `max(int, int, int)`: different counts, fine.
- `show(int, String)` and `show(String, int)`: different order, fine (but easy to confuse).
- `value()` returning `int` and `value()` returning `double`: **not** different. The return type does not count.
- `f(int a)` and `f(int b)`: **not** different. Parameter names do not count.

### Overloading in the standard library

You have been using overloaded methods since S1. `IO.println` can print many kinds of values, and `Math.max` has versions for `int`, `long`, `float` and `double`. `String.valueOf` has a version for nearly every type. `Arrays.toString` has one for `int[]`, one for `double[]`, and so on.

### When to use it, and when not to

Use overloading when the versions do **the same thing** with different input: `max` for two numbers and for three. Do not use the same name for methods that do different things only because the parameter lists differ. A reader should be able to guess what `print(x)` does without checking which version is called.

Overloading is also not about default values. If you need "the same method, but with an optional extra argument", an overload that calls the other one is the Java way: `greet(String name)` can call `greet(name, "Hello")`.

### Overloading versus overriding

The two words sound alike and mean different things. **Overloading** is several methods with the same name and *different* parameters in the same place. **Overriding** is a subclass replacing a method it inherited, with the *same* signature. We meet overriding in S5.

## Example

```java
int max(int a, int b) {
    return a > b ? a : b;
}

double max(double a, double b) {
    return a > b ? a : b;
}

int max(int a, int b, int c) {
    return max(max(a, b), c);
}

void main() {
    IO.println(max(3, 8));
    IO.println(max(2.5, 1.5));
    IO.println(max(4, 9, 6));
}
```

Output:

```
8
2.5
9
```

`max(3, 8)` uses the `int` version, `max(2.5, 1.5)` the `double` version, and `max(4, 9, 6)` the three-argument version, which calls the two-argument one twice. (The `a > b ? a : b` form is a *conditional expression*: it gives `a` if the condition is true and `b` otherwise.)

**Which version is picked?** Here, with versions for `int`, `long`, `double`, `String` and `char`:

```java
void print(int n)    { IO.println("int " + n); }
void print(long n)   { IO.println("long " + n); }
void print(double n) { IO.println("double " + n); }
void print(String s) { IO.println("String " + s); }
void print(char c)   { IO.println("char " + c); }

void main() {
    print(5);
    print(5L);
    print(5.0);
    print("five");
    print('5');
    byte b = 5;
    print(b);
    short s = 7;
    print(s);
    print(3.5f);
}
```

```
int 5
long 5
double 5.0
String five
char 5
int 5
int 7
double 3.5
```

Read the last three lines carefully. There is no `print(byte)` or `print(short)` or `print(float)`, so the compiler widens: a `byte` and a `short` go to the `int` version (the closest wider type), and a `float` goes to `double`. The literal `5` is an `int`, `5L` a `long`, `5.0` a `double`, `'5'` a `char`.

**Ambiguity.** If two versions fit equally well, the compiler refuses:

```java
void show(int a, double b) { IO.println("int, double"); }
void show(double a, int b) { IO.println("double, int"); }

void main() {
    show(1, 2);
}
```

```
error: reference to show is ambiguous
  both method show(int,double) in Amb and method show(double,int) in Amb match
```

Both can accept `(1, 2)` after a widening of one argument, and neither is better. You fix it by changing the call, for example `show(1, 2.0)`, or by removing one of the methods.

**Only the return type differs** does not compile:

```java
int value() { return 1; }
double value() { return 1.0; }
```

```
error: method value() is already defined in class Dup
```

## Common confusions

### "The compiler picks by the return type"

It never does. It looks at the number and types of the *arguments*. A call such as `double d = value();` does not influence which `value` is used, and that is why two methods that differ only by return type are not allowed.

### "The version is decided when the program runs"

For overloading, it is decided when the code is compiled. (This changes with *overriding* in S5, where the object's real type matters at run time.)

### Ambiguous calls with `null` and mixed numbers

Calls that mix numeric types, or pass `null`, are where "reference ... is ambiguous" errors happen most. If you see one, check which versions exist and make the call more exact.

### "Overloading and default parameters are the same"

Some languages have default parameter values; Java does not. Overloading is the Java way to offer "the short form and the long form" of a method.

> [!TIP]
> **From Python:**
> Python has no overloading: if you define a function twice with the same name, the second definition silently replaces the first. Python gets flexibility from default values (`def greet(name, greeting="Hello")`) and `*args`, and it is the *runtime* type of the arguments that matters. In Java, the several versions all exist at once and the compiler selects one.

> [!TIP]
> **From C#:**
> The same idea and nearly the same rules, including that the return type does not take part. C# can mix overloading with optional parameters and named arguments, which Java does not have, so Java code uses overloads in more places.

## Further reading

- [Java Language Specification, section 15.12.2](https://docs.oracle.com/javase/specs/): the precise algorithm for choosing a method; dense, but it is the source for the three phases described above.
