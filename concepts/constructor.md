---
type: concept
title: Constructor
summary: A special method that runs when an object is created and sets up its initial state.
sessions: [03-oop-classes]
status: complete
---

# Constructor

A special method that runs when an object is created and sets up its initial state.

## How it works

A constructor has the same name as the class and no return type. It runs once, during `new`:

```java
class Die {
    int sides;
    int value;

    Die(int sides) {
        this.sides = sides;
    }
}
```

`new Die(20)` calls it with `20`. The object exists and is already set up when `new` returns.

### `this`

Inside a constructor or method, `this` is the object that is being worked on. The parameter `sides` and the field `sides` have the same name, so `this.sides` means the field and `sides` means the parameter.

### The default constructor goes away

If you write no constructor, Java provides one without parameters. As soon as you write any constructor, that one disappears. After the class above, `new Die()` no longer compiles; you must give the number of sides. This is deliberate: it lets a class insist that every object is created with the data it needs.

### Several constructors

A class may have several constructors with different parameter lists, chosen the same way as in [overloading](overloading.md).

### Checking the input

A constructor is the natural place to refuse nonsense, for example a die with one side. Real error handling uses exceptions, which come in S7; until then the examples print a message and fall back to a safe value.

## Example

[DieConstructor.java](../sessions/03-oop-classes/examples/DieConstructor.java):

```java
class Die {
    int sides;
    int value;

    Die(int sides) {
        this.sides = sides;
    }

    void roll() {
        value = (int) (Math.random() * sides) + 1;
    }
}

void main() {
    Die d = new Die(20);
    IO.println("Sides: " + d.sides);
    d.roll();
    IO.println("Rolled: " + d.value);
}
```

Output (the second number varies, from 1 to 20):

```
Sides: 20
Rolled: 2
```

## Common confusions

### "A constructor returns the object"

It has no return type and returns nothing. `new` is what gives you the reference.

### "My constructor disappeared"

You wrote a constructor with parameters and then called `new Die()`. The default constructor exists only while you have written none.

### "`sides = sides;` does nothing useful"

Both names mean the parameter, so it assigns the parameter to itself. Write `this.sides = sides;`.

> [!TIP]
> **From Python:**
> The constructor replaces `__init__`: it has the class's name, no `self` parameter, and the fields must be declared in the class body.

> [!TIP]
> **From C#:**
> Same idea and same syntax. As in C#, declaring any constructor removes the implicit parameterless one.

## Further reading

- [Java Tutorials: Providing Constructors for Your Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html)
