---
type: concept
title: Record
summary: A compact class for plain data that generates its constructor, accessors, equals and toString.
sessions: [04-collections-generics]
status: complete
---

# Record

A compact class for plain data that generates its constructor, accessors, `equals` and `toString`.

## How it works

Many classes only carry a few values: a card has a suit and a rank, a point has an x and a y. Writing such a [class](class.md) by hand means fields, a [constructor](constructor.md), getters, `equals` and `toString`, all of it routine. A **record** declares all of that in one line:

```java
record Card(String suit, int rank) {
}
```

The names in parentheses are the **components**. The compiler generates:

- a constructor `Card(String suit, int rank)`;
- accessor methods `suit()` and `rank()`, without the `get` prefix;
- a `toString` that prints `Card[suit=hearts, rank=12]`;
- an `equals` that is true when all components are equal, and a matching `hashCode`.

### Records are immutable

The fields of a record are `final`: once the record is created they cannot be changed. `card.rank = 4;` does not compile. To "change" a record you create a new one. See [immutability](immutability.md). That is a limitation and also a safety feature: a record can be passed around and put in a [collection](collection.md) without anyone changing it behind your back.

### Records can have methods

A record may have methods, like any class:

```java
record Card(String suit, int rank) {
    String describe() {
        return rank + " of " + suit;
    }
}
```

It may not have extra instance fields beyond its components. If you need those, or the data changes, use a normal class.

### Value equality

Two separately created records with equal components are `.equals()`, even though they are different objects (`==` is `false`). This is what you have to write by hand for a normal class, as in S3's `equals` block.

### When to use one

Use a record for small, immutable bundles of data: coordinates, a card, a result. Use a normal class for objects that have changing state or real behavior, such as a `Die` that rolls or a `Player` whose hand grows. The record is the simpler tool for the first kind of job, and the wrong one for the second; see [KISS](kiss.md).

## Example

```java
record Card(String suit, int rank) {
}

void main() {
    Card a = new Card("hearts", 12);
    Card b = new Card("hearts", 12);

    IO.println(a);
    IO.println(a.suit());
    IO.println(a == b);
    IO.println(a.equals(b));
}
```

Output:

```
Card[suit=hearts, rank=12]
hearts
false
true
```

Changing a field does not compile:

```
error: cannot assign a value to final variable rank
```

## Common confusions

### "A record is a different thing from a class"

A record is a class, with a short way to write it. It has methods, it is created with `new`, and its objects are compared like any others.

### "Records have setters"

They do not. Fields are final. Create a new record with the changed value.

### "The accessors are `getSuit()`"

They are `suit()`. A record's accessor has the same name as the component.

### "`==` compares records by value"

It compares the reference. Use `.equals()`.

> [!TIP]
> **From Python:**
> Close to a `@dataclass(frozen=True)` or a `namedtuple`: a short declaration, value equality, a readable string form, no changes allowed.

> [!TIP]
> **From C#:**
> The same idea as C# 9 `record` types. A Java record is always immutable, and it has no `with` expression for copying with a change.

## Further reading

- [Java Tutorials: Record classes](https://docs.oracle.com/javase/tutorial/java/javaOO/record.html): the official explanation.
