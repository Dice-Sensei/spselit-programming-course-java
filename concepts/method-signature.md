---
type: concept
title: Method signature
summary: The name of a method together with the types of its parameters; it identifies the method.
sessions: [02-methods-arrays-strings]
status: draft
---

# Method signature

The name of a method together with the types of its parameters; it identifies the method.

## How it works

A *method* is a named piece of code that you can run (call) from elsewhere, as many times as you like. When you call it, you pass in values, and it can hand back a result. The **signature** is the part of a method's description that the compiler uses to decide *which* method you mean: its name plus the list of parameter types, in order.

```java
int square(int n) {
    return n * n;
}
```

The signature of this method is `square(int)`.

### The parts of a method

| Part | In the example | Meaning |
|---|---|---|
| Return type | `int` | The type of the value the method gives back. `void` means "nothing" |
| Name | `square` | How you call it. By convention `camelCase` and a verb or question: `isEven`, `greet` |
| Parameters | `(int n)` | The inputs: each has a type and a name. There can be zero, one or many, separated by commas |
| Body | `{ return n * n; }` | The code that runs |

The first line (return type, name and parameters) is the *method header*. The signature is only a part of it.

### What is in the signature and what is not

**In the signature:** the method name and the number, types and order of the parameters.

**Not in the signature:**
- the **return type**: `int value()` and `double value()` have the same signature, `value()`;
- the **parameter names**: `square(int n)` and `square(int x)` are the same method signature, `square(int)`;
- modifiers such as `public`, which we meet in S3.

So the signatures `max(int, int)`, `max(double, double)` and `max(int, int, int)` are three different signatures, and a class (here, the generated class of the file) can contain all of them. This is [overloading](overloading.md). In one class, you cannot have two methods with the *same* signature.

### Parameters and arguments

People mix these two words, and it is useful to keep them apart:

- A **parameter** is the variable in the method's declaration: `n` in `square(int n)`.
- An **argument** is the value you pass when you call it: `5` in `square(5)`.

When the call happens, each argument is **copied** into the matching parameter. Java always works this way: the method receives copies. This is the key to [reference vs value](reference-vs-value.md), which we meet again with arrays.

### Return values

A method that declares a return type must return a value of that type on **every** path through its body, with `return`. The `return` statement also ends the method at once. A `void` method returns nothing: it may use a bare `return;` to leave early, but a call to it cannot be used as a value.

The caller decides what to do with the result: store it (`int s = square(5);`), use it in an expression (`square(5) + 1`), pass it on (`IO.println(square(5))`), or ignore it.

### Scope

A variable declared inside a method (and the parameters) exists only inside that method. This is called its *scope*. Two different methods can both have a variable called `result` without interfering, because they are different variables. A variable declared inside a block `{ ... }` (for example inside a loop) exists only until the closing brace.

### Methods in a compact source file

In the [compact source files](compact-source-file.md) we use in the autumn, a method is just written next to `main`. The compiler puts them all into the generated class, and they can call each other directly by name.

## Example

```java
int square(int n) {
    return n * n;
}

boolean isEven(int n) {
    return n % 2 == 0;
}

void greet(String name) {
    IO.println("Hello, " + name + "!");
}

void main() {
    IO.println(square(5));
    IO.println(isEven(7));
    greet("Ada");
}
```

Output:

```
25
false
Hello, Ada!
```

Each call shows one idea. `square(5)` returns a number that is passed to `IO.println`. `isEven(7)` returns a `boolean`. `greet("Ada")` returns nothing and does its work by printing.

**Parameters are copies.** Changing a parameter inside a method does not change the caller's variable:

```java
void change(int number) {
    number = 99;
    IO.println("inside: " + number);
}

void main() {
    int number = 1;
    change(number);
    IO.println("outside: " + number);
}
```

```
inside: 99
outside: 1
```

The two variables are both called `number`, but they are two separate variables, one in each method.

**What the compiler says when the signature does not fit.** Calling a method with the wrong argument type or count:

```java
int square(int n) { return n * n; }

void main() {
    IO.println(square("5"));
    IO.println(square(1, 2));
}
```

```
error: method square in class Wrong cannot be applied to given types;
  required: int
  found:    String
  reason: argument mismatch; String cannot be converted to int
```

(The second call produces a similar error that says the argument counts differ.)

**Two methods with the same signature.** The return type does not count, so this does not compile:

```java
int value() { return 1; }
double value() { return 1.0; }
```

```
error: method value() is already defined in class Dup
```

**Using a variable outside its scope:**

```java
int twice(int n) {
    int result = n * 2;
    return result;
}

void main() {
    IO.println(twice(4));
    IO.println(result);
}
```

```
error: cannot find symbol
    IO.println(result);
               ^
  symbol:   variable result
```

**A path without `return`:**

```java
int broken(int n) {
    if (n > 0) {
        return 1;
    }
}
```

```
error: missing return statement
```

**Using a `void` method as a value:**

```java
void hello() { IO.println("hi"); }

void main() {
    int x = hello();
}
```

```
error: incompatible types: void cannot be converted to int
```

## Common confusions

### "The return type is part of the signature"

It is not, in Java. The compiler picks the method from the call's arguments, and a call like `value()` does not say what type you want back, so it could not choose between two methods that differ only in return type.

### Parameter and argument

If you say "parameter" for both, people will still understand you. But in an error message "required: int, found: String" means "the parameter type is `int` and the argument you passed is a `String`".

### "Changing the parameter changes my variable"

Not for `int`, `double`, `boolean` and the other primitive types: the method gets a copy. For arrays and other objects, the method gets a copy of the *reference*, so it can change the thing it points to. See [reference vs value](reference-vs-value.md).

### A method that prints versus a method that returns

`void greet(String name)` prints and returns nothing. `String greeting(String name)` returns the text and prints nothing. They are not interchangeable: a method that returns a value can be tested, reused and combined, while a method that only prints cannot. When you are unsure, return the value and print it in `main`.

### Methods and functions

In other languages the same thing is often called a *function*. In Java, code always lives in a class, so the usual word is *method*. In the compact source files we use, a method looks like a function, but it still belongs to the generated class.

> [!TIP]
> **From Python:**
> A `def` function is similar. Differences to remember: Java needs the types of all parameters and of the return value, and it has no default parameter values (`def f(x=1)`). Python needs a different function name for each variant; Java lets you reuse the name with different parameter types (see [overloading](overloading.md)).

> [!TIP]
> **From C#:**
> Practically the same: return type, name, typed parameters. Differences: C# has optional parameters and named arguments (`Greet(name: "Ada")`), and Java has neither. Java has no `out` or `ref` parameters; a method can return only one value.

## Further reading

- [Java Tutorials: Defining Methods](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html): the official introduction, including the exact definition of the signature.
