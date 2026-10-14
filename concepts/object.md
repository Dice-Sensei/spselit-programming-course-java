---
type: concept
title: Object
summary: A value that bundles data (state) with the operations that work on it (behavior).
sessions: [03-oop-classes]
status: complete
---

# Object

A value that bundles data (state) with the operations that work on it (behavior).

## How it works

An object has two parts:

- **State:** the data it holds, stored in its *fields*. A die has the number it currently shows. A bank account has its balance.
- **Behavior:** the things you can ask it to do, written as *methods*. A die can be rolled. An account can receive a deposit.

You work with an object through a variable that holds a [reference](reference-vs-value.md) to it. `String s = "hello";` makes a `String` object, and `s.toUpperCase()` asks that object to do something. You have been using objects since the first sessions, without the name.

### Why bundle state and behavior

Without objects, the data and the code that works on it drift apart: a balance in one variable, a `deposit` method somewhere else, and nothing that says they belong together. With an object, the data and the operations travel as one unit, and the program reads like a description of the problem: `ada.deposit(50)`.

### Every object comes from a class

An object is created from a [class](class.md) with `new`. The class says which fields and methods the object has; each object has its own values for the fields. Two `Account` objects have the same *shape* but separate balances.

### Objects and references

A variable of an object type holds a reference, not the object itself. `Die c = a;` copies the reference, so `c` and `a` are two names for one object. Everything in [reference vs value](reference-vs-value.md) applies.

### Primitives are not objects

`int`, `double`, `boolean` and `char` are [primitive types](primitive-type.md): plain values with no methods. You cannot write `5.toString()`. Everything else (strings, arrays, and your own classes) is an object type.

## Example

```java
class Account {
    int balance;

    void deposit(int amount) {
        balance = balance + amount;
    }
}

void main() {
    Account ada = new Account();
    ada.balance = 100;
    ada.deposit(50);
    IO.println("Ada: " + ada.balance);
}
```

Output:

```
Ada: 150
```

The full comparison of loose variables against an object is in [Bank.java](../sessions/03-oop-classes/examples/Bank.java).

## Common confusions

### "Object and class are the same thing"

A class is the blueprint; an object is one thing built from it. There is one `Account` class and as many `Account` objects as you create.

### "A variable holds the object"

It holds a reference to it. Declaring `Account a;` creates no object; `new Account()` does.

### "Everything in Java is an object"

Not quite. Primitive values are not. That is the main difference from Python, where even numbers are objects.

> [!TIP]
> **From Python:**
> In Python every value is an object. In Java the primitives (`int`, `double`, `boolean`, `char`) are not, and objects are created from classes you declare with `new`, with no `self` and with the fields declared up front.

> [!TIP]
> **From C#:**
> Very close to C# classes. The main differences are small: no `{ get; set; }` properties (you write getter methods), and no `struct`, so every class instance is a reference.

## Further reading

- [Java Tutorials: What Is an Object?](https://docs.oracle.com/javase/tutorial/java/concepts/object.html)
