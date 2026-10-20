---
type: concept
title: Composition
summary: Building a class out of other objects it holds ("has-a"), instead of inheriting from them.
sessions: [04-collections-generics]
status: complete
---

# Composition

Building a class out of other objects it holds ("has-a"), instead of inheriting from them.

## How it works

Real programs are made of several [classes](class.md) working together. The simplest way to connect them is for one class to keep another as a field. A `Deck` has a list of `Card`s. A `Player` has a hand. A car has an engine. This is **composition**, and people describe it as a **has-a** relationship.

```java
class Player {
    String name;
    List<Card> hand = new ArrayList<>();
}
```

The `Player` does not need to know how a `Card` is built; it holds cards and uses their methods. And it does not copy their code: it forwards work to the objects it holds.

### Has-a and is-a

There is a second way to relate classes, [inheritance](inheritance.md), which expresses **is-a**: a `Dog` is an `Animal`. Many beginners reach for inheritance first. In practice, composition is the more common tool and the more flexible one. Ask "is every X truly a Y?" If not, or if you are not sure, hold the object instead of inheriting from it. Inheritance comes in a later session.

### Each class has its own job

Composition lets you split a program into small classes that each look after one thing. The `Deck` shuffles and deals; the `Player` holds a hand. Neither knows how the other works inside. When the rules for dealing change, only the `Deck` changes. This is the **single responsibility** idea in [SOLID](solid.md#single-responsibility-principle).

### Containers, parts and ownership

A composed object usually creates its parts and owns them: a `Deck` makes its own list of cards. Sometimes an object only receives a reference to something that lives elsewhere (a `Player` is given cards by the deck). Both are composition; the difference is how long the part lives. Because the fields hold [references](reference-vs-value.md), two objects can share one part.

### Keep it proportionate

Do not add a class for every verb. A class earns its place when it has data or a job of its own. See [KISS](kiss.md).

## Example

```java
import java.util.ArrayList;
import java.util.List;

class Card {
    String suit;
    int rank;

    Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    String describe() {
        return rank + " of " + suit;
    }
}

class Player {
    String name;
    List<Card> hand = new ArrayList<>();

    Player(String name) {
        this.name = name;
    }

    void take(Card card) {
        hand.add(card);
    }

    int handSize() {
        return hand.size();
    }
}

void main() {
    Player ada = new Player("Ada");
    ada.take(new Card("hearts", 12));
    ada.take(new Card("spades", 3));
    IO.println(ada.name + " holds " + ada.handSize() + " cards");
    IO.println(ada.hand.get(0).describe());
}
```

Output:

```
Ada holds 2 cards
12 of hearts
```

The `Player` is composed of a name and a list of `Card` objects. It reaches the card's behavior through the list.

## Common confusions

### "Composition and inheritance are the same"

Composition says "has a": a `Player` has a hand. Inheritance says "is a": a `Dog` is an `Animal`. They solve related problems differently.

### "Putting objects in fields means copying them"

The field holds a reference. The object is not copied. Two players given the same `Card` object share it.

### "I need a class for every concept"

Only when it carries its own data or behavior. A plain variable is often enough.

> [!TIP]
> **From Python:**
> Exactly the same idea: an attribute that holds another object (`self.hand = []`). Python programmers also say "composition over inheritance".

> [!TIP]
> **From C#:**
> The same. A field or property of another class type is composition.

## Further reading

- [Java Tutorials: Classes and objects](https://docs.oracle.com/javase/tutorial/java/javaOO/index.html): objects that hold other objects.
