---
type: concept
title: Encapsulation
summary: Keeping an object's state private and changing it only through its own methods.
sessions: [03-oop-classes]
status: complete
---

# Encapsulation

Keeping an object's state private and changing it only through its own methods.

## How it works

If every field of an [object](object.md) is open, any code anywhere can set it to anything. A six-sided die can end up showing `99`, and the class can do nothing about it. Encapsulation closes that door:

1. Declare the fields `private`, so only the class's own code can touch them.
2. Offer `public` methods for what outsiders may do: `roll()` to change the value, `getValue()` to read it.

```java
class Die {
    private int sides;
    private int value;

    void roll() {
        value = (int) (Math.random() * sides) + 1;
    }

    int getValue() {
        return value;
    }
}
```

### The class protects its own rules

Because the only way to change `value` is `roll()`, the die can promise something it could not promise before: `value` is always between 1 and `sides`. The rule lives in one place, and no other code can break it.

### Getters and why not setters for everything

A method that returns a field (`getValue()`) is called a *getter*. You write a *setter* only when outsiders should really be allowed to set the field, and then it can check the new value first. Many fields never need one: a die's value is changed by rolling, not by setting.

### Hiding the how

Other code uses `roll()` without knowing how the number is produced. The class may change from `Math.random()` to something else, and nothing outside has to change. This is a first taste of [abstraction](abstraction.md).

### The compiler enforces it

Code outside the class that touches a private field fails to compile. See [PrivateAccess.java](../sessions/03-oop-classes/examples/PrivateAccess.java) and the access rules in [access modifier](access-modifier.md).

## Example

[Encapsulated.java](../sessions/03-oop-classes/examples/Encapsulated.java) has private fields, a constructor that rejects a die with fewer than two sides, and getters. Output (the roll varies, from 1 to 6):

```
A die needs at least 2 sides, using 6
Sides: 6
Rolled: 1
```

## Common confusions

### "Private means secret from the user"

It protects the code from other code in the program, not data from a person. A private field is still stored in plain memory.

### "Every field needs a getter and a setter"

No. Add only the operations the class should offer. A getter and setter for every field is the same as a public field with extra typing.

### "`private` means one object cannot see another"

`private` is per class, not per object. Two `Die` objects of the same class can read each other's private fields inside the class's own code.

### "It works in my compact file"

In a compact source file, classes are members of the file's hidden class and can see each other's `private` members. The class-versus-class check applies to classes declared as ordinary top-level classes, which is why the examples for this concept use the classic layout.

> [!TIP]
> **From Python:**
> Python has no real private: a leading underscore is only a convention. In Java the compiler refuses access.

> [!TIP]
> **From C#:**
> The same idea and the same keywords. Java has no properties, so a getter is an ordinary method, `getValue()`, rather than `Value { get; }`.

## Further reading

- [Java Tutorials: Controlling Access to Members of a Class](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)
