---
type: concept
title: Class
summary: The blueprint that describes what fields and methods the objects made from it have.
sessions: [03-oop-classes]
status: complete
---

# Class

The blueprint that describes what fields and methods the objects made from it will have.

## How it works

A class declaration lists what every object of that kind has:

```java
class Die {
    int sides = 6;
    int value;

    void roll() {
        value = (int) (Math.random() * sides) + 1;
    }
}
```

- `sides` and `value` are **fields**: each `Die` object has its own copy.
- `roll` is a **method**: it runs on one particular object and can read and change that object's fields.

### Making objects

`new Die()` builds an [object](object.md) from the blueprint and returns a reference to it. You can build as many as you like; they are independent:

```java
Die a = new Die();
Die b = new Die();
a.roll();      // changes a.value only
```

### Fields and local variables

A field belongs to the object and lives as long as the object does. A variable declared inside a method is local: it exists only while the method runs. Inside a method, a field is used by its plain name (`value`), or as `this.value` when you need to be explicit.

### Starting values

A field you do not set gets a default: `0` for numbers, `false` for `boolean`, `null` for references. Local variables get no default and must be set before they are used. Use a [constructor](constructor.md) when the object needs real starting values.

### Every class defines a type

`Die` is now a type like `int` or `String`. You can declare `Die d`, pass a `Die` to a method, or keep several in an array: `Die[] dice = new Die[3];` (an array of references, all `null` until you fill them).

## Example

[Die.java](../sessions/03-oop-classes/examples/Die.java) (Java 25 style) and [DieOld.java](../sessions/03-oop-classes/examples/DieOld.java) (classic style) declare the class above and roll one. Output (the number varies):

```
Rolled: 4
```

## Common confusions

### "The class holds the values"

The class describes the fields; each object holds its own values. Changing `a.value` does not change `b.value`.

### "A class needs its own file"

In these examples several classes share one file, which is allowed. In larger programs each public class gets its own file.

### "Forgetting `new`"

`Die d;` declares a variable and creates nothing. Using `d.roll()` before `d = new Die();` does not compile, and a variable that holds `null` fails at run time.

> [!TIP]
> **From Python:**
> There is no `self` parameter and no `__init__`: methods see the fields directly, and the constructor has the class's name. Fields are declared in the class body, with a type.

> [!TIP]
> **From C#:**
> Class declarations are almost the same. Java has no properties, and no `struct`.

## Further reading

- [Java Tutorials: Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/classes.html)
