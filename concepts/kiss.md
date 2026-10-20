---
type: concept
title: KISS
summary: Keep it simple; use the simplest solution that solves the problem you actually have.
sessions: [04-collections-generics]
status: complete
---

# KISS

Keep it simple; use the simplest solution that solves the problem you actually have.

## How it works

KISS is a design guideline, often expanded as "keep it simple, stupid". Every extra class, collection or abstraction is something you have to write, read, test and later change. If a simpler tool does the same job, choose it. Complexity should be added when a real problem asks for it, not in case it might.

### Signs of too much

- **A tool bigger than the problem:** a [`HashMap`](collection.md) holding two values you know in advance, when two variables would do.
- **Machinery for a future that has not arrived:** a generic class written for types you never use, or a hierarchy of classes with one member.
- **Indirection that hides the point:** reading the code means jumping through five files to learn what one `if` could say.

### Simple does not mean short

The shortest code is not always the simplest. Simple means easy to read and easy to change. A clear five-line `if` beats a clever one-liner that needs a comment.

### How to apply it

1. Solve the problem in the plainest way you can.
2. Notice when that way starts to hurt: the same code appears three times ([DRY](dry.md)), or a change needs edits in many places.
3. Add the next-simplest structure that removes that pain, and no more.

KISS pulls against DRY if you push either too far. A little repetition is often cheaper than an abstraction nobody understands, and a pile of copies is cheaper to fix than a pile of wrong abstractions. The judgement is yours, and it comes from having tried both.

## Example

Two ways to turn a score into a grade.

```java
import java.util.*;

void main() {
    int score = 72;

    // Heavy: a map holding two fixed cases.
    Map<Boolean, String> grades = new HashMap<>();
    grades.put(true, "pass");
    grades.put(false, "fail");
    IO.println(grades.get(score >= 50));

    // Simple: an if.
    String grade;
    if (score >= 50) {
        grade = "pass";
    } else {
        grade = "fail";
    }
    IO.println(grade);
}
```

Output:

```
pass
pass
```

Both are correct. The second has no collection, no boxing of `true` and `false`, and a reader sees the rule at once.

## Common confusions

### "Simple means beginner code"

Professionals follow KISS the most. The goal is code another person can understand in a minute.

### "KISS means never use a collection or a class"

No. When the data really is a growing group or a lookup, a collection is the simple tool. KISS asks whether the tool fits the problem.

### "I should plan for everything now"

You cannot know what will change. Keep the code easy to change, and change it when the need appears.

> [!TIP]
> **From Python:**
> The same idea as "simple is better than complex" in the Zen of Python (`import this`).

> [!TIP]
> **From C#:**
> The same principle with the same name. Java and C# both make it easy to over-build with classes and interfaces, so it applies to both.

## Further reading

- [KISS principle on Wikipedia](https://en.wikipedia.org/wiki/KISS_principle): the origin of the phrase.
