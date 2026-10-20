---
type: concept
title: DRY
summary: Don't repeat yourself; each piece of knowledge lives in one place.
sessions: [04-collections-generics]
status: complete
---

# DRY

Don't repeat yourself; each piece of knowledge lives in one place.

## How it works

DRY is a design guideline: if the same idea is written in several places, a change to the idea has to be made in all of them, and one of them will be forgotten. Writing it once, and using it from everywhere, means one change fixes everything.

### What counts as repetition

- **Copy-pasted code with a number changed:** three blocks that differ only in `card1`, `card2`, `card3` want a loop over a list.
- **The same value in several places:** the number of cards in a deck written as `52` in six places belongs in one named variable.
- **The same logic in several methods:** extract a method and call it.

The thing to avoid is repeated *knowledge*, not repeated *characters*. Two lines that look alike but mean different things can stay separate.

### The tools in this course

Loops repeat an action; [methods](method-signature.md) give a piece of code a name; [classes](class.md) put data and its behavior together; a [collection](collection.md) replaces many numbered variables with one group.

### When DRY goes too far

Merging two things that only look alike creates a tangle: one helper that takes five flags to behave like both. If you have to add a special case to the shared code for each user, it is worse than two copies. A common rule of thumb is to wait until you have seen the same code three times, and then remove the duplication. DRY goes with [KISS](kiss.md): a repeated line is cheaper than a confusing abstraction.

## Example

Before: three cards, three nearly identical lines.

```java
void main() {
    String card1 = "hearts 12";
    String card2 = "spades 3";
    String card3 = "clubs 7";
    IO.println("Card: " + card1);
    IO.println("Card: " + card2);
    IO.println("Card: " + card3);
}
```

After: the repeated line is written once.

```java
import java.util.*;

void main() {
    List<String> cards = List.of("hearts 12", "spades 3", "clubs 7");
    for (String card : cards) {
        IO.println("Card: " + card);
    }
}
```

Both print:

```
Card: hearts 12
Card: spades 3
Card: clubs 7
```

A fourth card is now one more string in the list, with no new code.

## Common confusions

### "DRY means never type the same thing twice"

It is about knowledge, not text. Two `for` loops that both start with `int i = 0` do not violate DRY.

### "More abstraction is always more DRY"

An abstraction that nobody can follow costs more than the duplication it removed. Prefer a little repetition to the wrong abstraction.

> [!TIP]
> **From Python:**
> The same principle with the same name. Python's comprehensions and functions are the usual way to remove repetition; in Java you use loops, methods and collections.

> [!TIP]
> **From C#:**
> Identical to C#. The tools are the same too: methods, loops and generic collections.

## Further reading

- [Don't repeat yourself on Wikipedia](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself): the origin of the principle in *The Pragmatic Programmer*.
