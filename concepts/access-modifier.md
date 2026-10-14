---
type: concept
title: Access modifier
summary: A keyword such as public or private that says who may use a field, method or class.
sessions: [03-oop-classes]
status: complete
---

# Access modifier

A keyword such as `public` or `private` that says who may use a field, method or class.

## How it works

You write an access modifier in front of a field, a method, a constructor or a class. Java has four levels, from most to least open:

| Modifier | Who may use it |
|---|---|
| `public` | any code |
| `protected` | the same package, and subclasses (see S5) |
| *(none)* | the same package only ("package access") |
| `private` | only the class it is declared in |

For now you need two: `public` for what a class offers to the outside, and `private` for what it keeps to itself. This is the tool behind [encapsulation](encapsulation.md).

### A sensible default

Make fields `private` and methods `public` unless there is a reason not to. It is easy to open something later and hard to close it once other code depends on it.

### Helper methods

A `private` method is a helper that exists only to be called by the class's other methods. [AccessModifiers.java](../sessions/03-oop-classes/examples/AccessModifiers.java) has a private `log()` that `increment()` calls.

### Packages

A package is a named group of related classes. Our single-file examples have no `package` line, so everything is in one unnamed package, and "package access" means "any class in the same folder". Packages become more important in larger programs.

## Example

```java
class Counter {
    private int count;

    private void log() {
        System.out.println("count is now " + count);
    }

    public void increment() {
        count++;
        log();
    }

    public int getCount() {
        return count;
    }
}
```

From outside the class, `c.increment()` and `c.getCount()` compile; `c.count = 10` and `c.log()` do not. Output of the full example:

```
count is now 1
count is now 2
2
```

## Common confusions

### "Leaving the modifier off is the same as public"

It is package access, which is wider than `private` and narrower than `public`. In a one-folder program it behaves like `public`, which hides mistakes until the program grows.

### "`private` hides it from the whole file"

It limits access to the class. Two classes in the same file are still separate classes. (A [compact source file](compact-source-file.md) is an exception: see [encapsulation](encapsulation.md).)

> [!TIP]
> **From Python:**
> Python has no enforced access levels, though there is gentlemen agreement that things starting with '_' (underscore) are private; Java has four, and the compiler checks them.

> [!TIP]
> **From C#:**
> `public` and `private` mean the same. `protected` is similar. Package access corresponds roughly to `internal`.

## Further reading

- [Java Tutorials: Controlling Access to Members of a Class](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)
