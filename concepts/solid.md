---
type: concept
title: SOLID
summary: Five design guidelines for object-oriented code (single responsibility, open/closed, Liskov substitution, interface segregation, dependency inversion), to be used with judgement.
sessions: [04-collections-generics]
status: complete
---

# SOLID

Five design guidelines for object-oriented code (single responsibility, open/closed, Liskov substitution, interface segregation, dependency inversion), to be used with judgement.

## How it works

SOLID is a name made of the first letters of five guidelines for designing classes, collected by Robert C. Martin in the early 2000s. They describe code that is easy to change: when a requirement changes, few places need to be edited, and editing one place does not break another.

They are **guidelines, not laws**. They grew out of large programs, and applying all five to a small one makes it worse: more classes, more indirection, nothing gained. That is over-engineering, which is the opposite of [KISS](kiss.md). Use them as questions to ask when a class is painful to change, not as a checklist to satisfy.

### Single responsibility principle

*S: a class should have one reason to change.*

A class that holds the cards **and** decides how the table is printed has two reasons to change: the rules of dealing and the look of the output. Split it, so each class does one job.

```java
// Too many jobs: holds the cards AND prints the table.
class MessyDeck {
    List<Card> cards = new ArrayList<>();
    void printTable() { /* prints every card */ }
}

// One job each.
class Deck {
    List<Card> cards = new ArrayList<>();
}

class Table {
    void show(Deck deck) { /* prints every card */ }
}
```

Now a new way to print touches `Table` only. The same idea shows in `Deck` and `Player` in [composition](composition.md): the deck deals, the player holds a hand. This letter is the one S4 uses; see the [S4 session page](../sessions/04-collections-generics/README.md#composition-deck-and-player-if-time-allows).

Do not overdo it. A class with two closely related methods is fine. Split when the two parts change for different reasons.

### Open/closed principle

*O: code should be open for extension and closed for modification.*

You should be able to add new behavior (a new kind of card game, say) by adding code, not by editing and risking what already works. It relies on [inheritance](inheritance.md), [polymorphism](polymorphism.md) and interfaces.

### Liskov substitution principle

*L: wherever a parent type is expected, any child type must work.*

If `Dog` extends `Animal`, then code written for an `Animal` must not break when handed a `Dog`. It is a rule for doing [inheritance](inheritance.md) correctly.

### Interface segregation principle

*I: do not force a class to depend on methods it does not use.*

Several small, focused contracts are better than one large one. It relies on interfaces, which come in a later session.

### Dependency inversion principle

*D: depend on abstractions, not on concrete details.*

A class should rely on a description of what it needs (see [abstraction](abstraction.md)), not on one particular class that provides it, so the provider can be replaced.

### How to use it

1. Write the simple version first.
2. When one change forces edits in many places, or one class keeps growing, look at the five letters for a reason.
3. Fix the specific pain. Do not apply a principle just because it exists.

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

class Deck {
    List<Card> cards = new ArrayList<>();
}

class Table {
    void show(Deck deck) {
        for (Card card : deck.cards) {
            IO.println("[ " + card.describe() + " ]");
        }
    }
}

void main() {
    Deck deck = new Deck();
    deck.cards.add(new Card("hearts", 12));
    deck.cards.add(new Card("spades", 3));
    new Table().show(deck);
}
```

Output:

```
[ 12 of hearts ]
[ 3 of spades ]
```

The `Deck` holds the cards, and the `Table` decides how they look. Changing the format, for example to show suits as symbols, means editing `Table` alone.

## Common confusions

### "I must follow all five rules in every program"

No. They are guidelines for code that is hard to change. A short program may not need any of them.

### "Single responsibility means one method per class"

It means one *reason to change*. A class can have many methods that all serve one purpose.

### "More classes means better design"

Often the opposite. A class with no job of its own is noise.

### "SOLID is object-oriented law"

It is advice from experienced programmers, and sensible people disagree about how far to take it.

> [!TIP]
> **From Python:**
> The same advice applies to Python, where it is written about less because Python's flexible typing relaxes some of it. The single-responsibility idea carries over unchanged.

> [!TIP]
> **From C#:**
> SOLID is very popular in the C# world, and the five letters are used in the same words there. Frameworks like ASP.NET are built around them.

## Further reading

- [SOLID on Wikipedia](https://en.wikipedia.org/wiki/SOLID): the five principles and their history.
