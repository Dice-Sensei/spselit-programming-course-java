---
type: concept
title: Generics
summary: Type parameters in angle brackets that let one class work with many types while the compiler still checks them.
sessions: [04-collections-generics]
status: complete
---

# Generics

Type parameters in angle brackets that let one class work with many types while the compiler still checks them.

## How it works

`ArrayList` is written once, but you can have a list of strings, a list of cards or a list of numbers. The class does this with a **type parameter**, written in angle brackets after its name. When you use the class you supply a **type argument**: `ArrayList<String>` is the `ArrayList` whose elements are `String`s. This feature is called *generics*.

The compiler uses the type argument to check your code. Putting an `int` into an `ArrayList<String>` is a compile error, and `list.get(0)` on an `ArrayList<String>` already has the type `String`, so no cast is needed.

### Reading generic types

Read from the outside in:

- `ArrayList<Card>`: a list of `Card`s.
- `HashMap<String, Integer>`: a map whose keys are `String`s and whose values are `Integer`s. A map has two type parameters, key first.
- `Map<String, List<Card>>`: a map from `String` to a list of `Card`s. A type argument can itself be a generic type.

### The diamond

On the right of `new` you can leave the type argument out: `ArrayList<Card> hand = new ArrayList<>();`. The empty `<>` is called the *diamond*, and the compiler fills in `Card` from the left side. You may also write the type on both sides; it means the same.

### Why generics exist

Before generics (Java 5), a list held `Object`, and you cast what you got out. The compiler could not tell a list of strings from a list of anything, so a wrong element only failed when the program ran, with a `ClassCastException`. Generics move that check to compile time, which is the idea behind [static typing](static-typing.md): a mistake the compiler finds is much cheaper than one a user finds.

### Primitives are not allowed

A type argument must be a class, so `ArrayList<int>` does not compile. You write `ArrayList<Integer>`; see [wrapper classes](wrapper-class.md) and [autoboxing](autoboxing.md).

### Generics are checked, then erased

After the compiler has checked the types, the type arguments are removed from the compiled code. This is called *type erasure*. The practical effects are small, and you can see one: `new ArrayList<String>()` and `new ArrayList<Integer>()` are objects of exactly the same class. It is why you cannot write `new T()` or ask at run time what a list's type argument was.

### Using versus writing

Everything in this course so far *uses* generic classes from the library. You can also write your own (`class Box<T> { ... }`), and that is a larger step: it only pays off when the same code would otherwise be written twice for different types. Until you feel that duplication, a plain class is simpler; see [KISS](kiss.md) and [DRY](dry.md). The advanced parts, such as wildcards (`? extends`), are not part of this course.

## Example

```java
import java.util.ArrayList;

void main() {
    ArrayList<Object> things = new ArrayList<>();
    things.add("hello");
    Integer number = (Integer) things.get(0);
    IO.println(number);
}
```

The compiler accepts this, and the program fails when it runs:

```
Exception in thread "main" java.lang.ClassCastException: class java.lang.String cannot be cast to class java.lang.Integer
```

With a typed list the mistake never gets that far:

```java
import java.util.ArrayList;

void main() {
    ArrayList<Integer> numbers = new ArrayList<>();
    numbers.add("hello");
}
```

```
error: no suitable method found for add(String)
      (argument mismatch; String cannot be converted to Integer)
```

## Common confusions

### "`ArrayList<Object>` can be used instead of any list"

It can hold anything, but you pay for it with casts and run-time failures. Use the real element type.

### "I have to write `<T>` in my own classes"

No. Using `ArrayList<String>` does not require you to write any generic code.

### "`<>` means the type is missing"

The diamond means "work it out from the left". The type is still there and still checked.

### "`ArrayList<int>` should work"

Primitives are not objects. Use `ArrayList<Integer>`.

> [!TIP]
> **From Python:**
> Python type hints (`list[str]`) look similar, but they are optional and not enforced when the program runs. In Java the compiler refuses code that breaks them.

> [!TIP]
> **From C#:**
> Very similar to C# generics, with one big difference: C# keeps the type arguments at run time, while Java erases them. In Java you cannot write `new T()` or `typeof(T)`, and `List<int>` is not allowed, where C# allows it.

## Further reading

- [Java Tutorials: Generics](https://docs.oracle.com/javase/tutorial/java/generics/index.html): the official introduction.
