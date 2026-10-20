---
type: session
title: Collections, generics and composition
session: 4
status: skeleton
blocks:
  - {name: arraylist, status: planned}
  - {name: hashmap, status: planned}
  - {name: generics, status: planned}
  - {name: record, status: planned, optional: true}
  - {name: composition, status: planned, optional: true}
concepts: [collection, generics, wrapper-class, autoboxing, record, composition, kiss, dry, solid]
---

# S4 — Collections, generics and composition

## Goal

By the end of this session you can keep a group of objects in an `ArrayList`, look values up in a `HashMap`, and read a type such as `HashMap<String, List<Card>>` and say what it holds. Just as important, you can say when **not** to use them: a simple variable or an `if` is often the better tool.

> [!NOTE]
> This page builds on the `Card` class from S3. Read [class](../../concepts/class.md) and [array](../../concepts/array.md) first if you missed that session. You need JDK 25 or newer; if `java Hello.java` fails with an error mentioning "preview" or "unnamed class", see the setup guide's [common problems](../../setup.md#common-problems).

## ArrayList and the for-each loop

An [array](../../concepts/array.md) has a fixed size, which is awkward when a group grows and shrinks, such as the cards in a hand. An `ArrayList` is a [collection](../../concepts/collection.md): an object that holds a group of other objects in order and grows and shrinks as you add and remove.

```java
ArrayList<Card> hand = new ArrayList<>();
hand.add(new Card("hearts", 12));
hand.add(new Card("spades", 3));
```

The part in angle brackets says what the list holds; the next blocks explain it. The main methods:

- `add(x)` puts `x` at the end;
- `get(i)` returns the element at index `i`, counted from **0** like an array;
- `remove(i)` removes the element at index `i` and shifts the rest left;
- `size()` is the number of elements (a method, so it has parentheses, unlike an array's `length`).

The **for-each loop** goes through every element without an index:

```java
for (Card card : hand) {
    IO.println(card.describe());
}
```

Read it as "for each `card` in `hand`". Examples: [ArrayListDemo.java](examples/ArrayListDemo.java) uses a list of cards. The same job is done twice in [ScoresArray.java](examples/ScoresArray.java) and [Scores.java](examples/Scores.java): removing one score from four. With an array you shift the elements by hand and count how many are in use. With an `ArrayList` it is one line, `scores.remove(1)`.

> [!TIP]
> **From Python:**
> An `ArrayList` is the Java version of a Python `list`: it grows, and `add` is `append`. The differences are that all elements have one type, you write `list.get(i)` instead of `list[i]`, and there is no slicing.

### Things that go wrong

- Asking for an index that does not exist stops the program with an `IndexOutOfBoundsException`. For a list of one element, `get(1)` fails with `Index 1 out of bounds for length 1`.
- Do not add or remove while a for-each loop is walking the same list. It usually stops with a `ConcurrentModificationException`.
- On a list of `Integer`, `remove(1)` removes the element at **index** 1, while `remove(Integer.valueOf(1))` removes the **value** 1. With `[10, 5, 1]` the first gives `[10, 1]` and the second `[10, 5]`.

### Simple or heavy?

Without a list you might write `Card card1`, `Card card2`, `Card card3` and the same code three times, and a fourth card means editing the program. A list and a loop put the repeated part in one place.

> [!TIP]
> **DRY: Don't repeat yourself.**
> If you copy and paste code with a number changed, you probably want a loop and a collection. See [DRY](../../concepts/dry.md). The opposite also holds: for exactly two values that will never change, two variables are simpler than a list.

### Exercises

**Core**

- Store five names in an `ArrayList<String>`, add one more, remove the first, and print all of them with a for-each loop.

**Extra**

- Keep a list of scores and print their sum and average.

**Challenge**

- Given a list that contains some names more than once, build a new list without duplicates. Do not use a `Set`.

## HashMap: lookup by key

A list finds things by position. A `HashMap` finds things by **key**: you store pairs, and ask "what belongs to this key?". Counting how often each word appears is the classic job: the word is the key and the count is the value.

```java
HashMap<String, Integer> counts = new HashMap<>();
counts.put("to", 1);
counts.put("to", 2);          // same key: the old value is replaced
int n = counts.get("to");     // 2
boolean has = counts.containsKey("xyz");   // false
```

- `put(key, value)` stores a pair, or replaces the value if the key is already there;
- `get(key)` returns the value, or `null` if there is no such key;
- `containsKey(key)` asks whether a key exists;
- `size()` is the number of pairs.

`HashMap<String, Integer>` means "keys are `String`s, values are `Integer`s"; the next block explains the angle brackets. Example: [MapDemo.java](examples/MapDemo.java) counts the words of a sentence.

> [!WARNING]
> A `HashMap` makes **no promise about the order** of its entries. Look values up by key. To go through all of them in alphabetical order, loop over `new TreeMap<>(counts).keySet()` (a `TreeMap` keeps its keys sorted) and call `counts.get(word)` for each.

> [!TIP]
> **From Python:**
> A `HashMap` is a Python `dict`, but `d[key]` is `map.get(key)`, `d[key] = v` is `map.put(key, v)`, and a missing key gives `null` instead of an error.

### Simple or heavy?

[MapOverkill.java](examples/MapOverkill.java) stores a person's name and age in a `HashMap`, and then in two variables. Both print `Ada is 36`. The map is longer, loses the types (the values are `Object`s) and invites typos in the keys. When there are two values that you know in advance, and will not look up dynamically, plain variables, an `if`, or later a small class are better.

> [!TIP]
> **KISS: Keep it simple.**
> Pick the simplest tool that solves the problem you have, not the one that sounds most powerful. See [KISS](../../concepts/kiss.md). A map is for lookups that depend on data you do not know in advance.

### Exercises

**Core**

- Count how often each word occurs in a sentence you choose, then print the count of three words you pick.

**Extra**

- Group words by their first letter: the key is the letter, and the value is how many words start with it.

**Challenge**

- Find the word that occurs most often in a sentence.

## Generics, wrapper types and autoboxing

You have now typed `ArrayList<Card>` and `HashMap<String, Integer>`. The part in angle brackets is a **type argument**, and the feature is called [generics](../../concepts/generics.md): one class (`ArrayList`) that works with many types, while the compiler still checks which type you chose. `ArrayList<Card>` is a list that holds only `Card`s.

### Why the brackets exist

Without them a list holds `Object`, and anything may go in. [RawVsGeneric.java](examples/RawVsGeneric.java) puts a `String` into an `ArrayList<Object>` and later treats it as a number. The compiler allows the cast, and the program fails while it runs:

```
Exception in thread "main" java.lang.ClassCastException: class java.lang.String cannot be cast to class java.lang.Integer
```

With a type argument, the same mistake is caught before the program starts, by the [static typing](../../concepts/static-typing.md) you met in S1. [GenericRejects.java](examples/GenericRejects.java) does not compile on purpose: it adds a `String` to an `ArrayList<Integer>`, and the compiler says

```
error: no suitable method found for add(String)
      (argument mismatch; String cannot be converted to Integer)
```

A mistake found by the compiler is cheaper than one found by a user.

### Reading a generic type

Read from the outside in. `Map<String, List<Card>>` is "a map from `String` to a list of `Card`s", for example a player's name and their hand. [GenericTypes.java](examples/GenericTypes.java) builds one and reads a hand back.

### Primitives and wrapper types

A type argument must be an object type, so `ArrayList<int>` is an error (`unexpected type; required: reference, found: int`). For each primitive there is a **wrapper class** that holds the same value as an object: `Integer` for `int`, `Double` for `double`, `Boolean` for `boolean`, `Character` for `char`. See [wrapper class](../../concepts/wrapper-class.md).

Java converts between the two for you. This is **autoboxing** (`int` to `Integer`) and **unboxing** (back); see [autoboxing](../../concepts/autoboxing.md). `numbers.add(5)` boxes the `5`, and `int first = numbers.get(0);` unboxes it. Example: [Boxing.java](examples/Boxing.java).

> [!WARNING]
> Compare wrapper objects with `.equals()`, not `==`. In [Boxing.java](examples/Boxing.java), `==` on two `Integer`s holding `100` gave `true`, and on two holding `1000` it gave `false`, while `.equals()` gave `true` both times. This is the `==` versus `.equals()` question from S2 again.

### Should I write my own generic class?

Not yet. For now you **use** generics from the library. Writing your own `<T>` class is worth it only when you have the same code twice for two different types. Until that happens, a plain class is simpler.

> [!TIP]
> **KISS: Keep it simple.**
> Generics make the compiler check your collections, and that is the whole benefit. Reaching for them in your own classes before you need them adds symbols without adding value. See [KISS](../../concepts/kiss.md).

> [!TIP]
> **From Python:**
> A Python list can hold anything, so there is nothing like `ClassCastException` to fear until you use the value. Java makes you say what the list holds, and checks it before the program runs, much like type hints that are enforced.

### Exercises

**Core**

- Fix a program that stores a `String` in a list of numbers. Then read these types and say in words what each holds: `ArrayList<String>`, `HashMap<String, Integer>`, `Map<String, List<Card>>`.

**Extra**

- Rewrite a program that uses `ArrayList<Object>` with casts so that it uses a typed list and needs no casts.

**Challenge**

- For each of five small tasks you choose (for example "store a person's name and age", "keep all the words of a text", "count how often each word occurs"), decide whether it needs a collection, and write one sentence why.

## Records (if time allows)

The S3 `Card` class needs a constructor, fields and (later) `toString` and `equals` just to carry two values. A [record](../../concepts/record.md) is a short way to declare a class that only carries data:

```java
record Card(String suit, int rank) {
}
```

That one line gives you the constructor, accessor methods `suit()` and `rank()`, a readable `toString` and an `equals` that compares the values. Its fields are `final`: they cannot change after the object is created. Example: [CardRecord.java](examples/CardRecord.java) prints `Card[suit=hearts, rank=12]`, and shows two separately created equal cards where `a == b` is `false` and `a.equals(b)` is `true`.

### Simple or heavy?

A record is the simpler tool for plain data that never changes. Use a normal class when the object has state that changes (a `Die` that rolls, a `Player` whose hand grows). Do not turn a class into a record just because it is shorter.

> [!TIP]
> **KISS: Keep it simple.**
> Choose the form that says what you mean: a record for "just these values", a class for "a thing that does something". See [KISS](../../concepts/kiss.md).

### Exercises

**Core**

- Turn your `Card` into a record and compare two equal cards with `==` and with `.equals()`.

**Extra**

- Put several `Card` records in an `ArrayList` and print the one with the highest rank.

**Challenge**

- Add a method to the record that returns a readable description such as `12 of hearts`. (Checking that the rank is valid comes with exceptions, in a later session.)

## Composition: Deck and Player (if time allows)

So far a `Card` has been a class on its own. A real game is made of several classes that work together. [Composition](../../concepts/composition.md) means building a class out of other objects it holds: a `Deck` **has** cards, and a `Player` **has** a hand. People call it "has-a".

```java
class Deck {
    List<Card> cards = new ArrayList<>();
    // shuffle() and deal() work on cards
}

class Player {
    String name;
    List<Card> hand = new ArrayList<>();
    // take(card) adds to hand
}
```

Example: [DeckAndPlayer.java](examples/DeckAndPlayer.java) builds a 52-card deck, shuffles it and deals five cards to a player. The deal is random, so your output differs; this is one possible run:

```
cards in deck: 52
cards left: 47
Ada holds 5 cards:
  4 of spades
  13 of hearts
  13 of diamonds
  13 of clubs
  10 of spades
```

### One job per class

Notice what each class does *not* do. The `Deck` knows the cards and how to shuffle and deal them, but it does not print anything and does not know who is playing. The `Player` knows its hand, but not how the whole deck is built. Each class has one reason to change. This is the **S** of [SOLID](../../concepts/solid.md#single-responsibility-principle): the single responsibility principle.

### Simple or heavy?

Two classes for a deck and a player are right here, because each holds its own changing data. A separate class for every tiny action ("a `CardDealer`", "a `HandPrinter`") would be heavy. Add a class when it has a job of its own.

> [!TIP]
> **KISS: Keep it simple.**
> Split a class when it has two reasons to change, not before. See [KISS](../../concepts/kiss.md).

### Exercises

**Core**

- Write a `Deck` of 52 cards with `shuffle()` and `deal()`. Deal three cards and print them.

**Extra**

- Write a `Player` with a hand, and a method that returns the sum of the ranks in the hand.

**Challenge**

- Two players take turns drawing from one deck. After five rounds, print who holds the higher total.

## Lecture notes

<!-- filled after the lecture -->
