---
type: guide
title: Conventions for AI tools and the course leader
status: draft
---

# AGENTS.md

Read this before editing anything in this repository.

## Purpose and audience

This repository is a Java course wiki for high school students (2nd year and up) who have programmed before in any language. It is a Git repository and an Obsidian vault. Pages must render correctly on GitHub and in Obsidian. The course is optional, weekly, project-based and drop-in: a student may miss sessions or leave early.

## Content language

- All content is in **English**.
- Czech explanations appear only as callouts: `> [!NOTE]` whose first content line is `**Česky:**`.
- The lecture is spoken in Czech, but the pages are not.

## Repo layout

```
README.md          start page: overview, schedule, how the wiki works
AGENTS.md          this file
setup.md           installing the JDK and IntelliJ, common problems
templates/         session.md, concept.md
sessions/NN-name/  README.md (session page), examples/, exercises/, live/
concepts/          README.md (index) and one page per "big word"
advent-of-code/    added later
my-work/           git-ignored; students' own code
```

- `examples/`: runnable examples shown on the page.
- `exercises/`: starter code.
- `live/`: code written live in the lecture, pushed afterwards.

## Page types and frontmatter

Every Markdown page (except `.obsidian/` content) has YAML frontmatter with at least `type`, `title` and `status`.

Session page:

```yaml
---
type: session            # session | concept | guide | index
title: Kickoff and first programs
session: 1
status: skeleton         # session pages: skeleton → updated (after the lecture)
blocks:
  - {name: setup, status: planned}            # planned | done | moved-to-NN
  - {name: first-program, status: planned}
  - {name: control-flow, status: planned}
  - {name: methods, status: planned, optional: true}
concepts: [jdk, jvm, compiler, bytecode, static-typing, primitive-type]
---
```

Concept page:

```yaml
---
type: concept
title: Compiler
summary: Translates Java source code into bytecode before the program runs.
sessions: [01-kickoff]
status: draft            # draft | complete
---
```

The concept page's `summary` is the single source for its short explanation. Inline explanations on session pages must agree with it.

## Concept pages

Write every concept page in full when you create it. Never create a stub or a page that is "to be extended later". Start from `templates/concept.md`. Each page has:

- the summary paragraph, then `## How it works` with `###` subsections that go beyond the lecture's short description, for students who want to understand more;
- `## Example` with runnable code and its real output (run it before you write it down);
- `## Common confusions` with one `###` entry per misconception;
- `> [!TIP]` **From Python:** and **From C#:** comparisons where they add something;
- `## Further reading` with links to the official documentation.

Only state facts you have verified by running code or that are well established. Prefer including more; the leader culls. Status is `draft` when written, and `complete` once the leader has reviewed it.

## Formatting rules

- **Links:** relative Markdown links only, for example `[compiler](../../concepts/compiler.md)`. Never use `[[wikilinks]]`. Every link must resolve from its own file's folder.
- **File names:** lowercase with hyphens (`static-typing.md`). Session folders are `sessions/NN-name/`.
- **Callouts:** only `NOTE`, `TIP`, `IMPORTANT`, `WARNING` and `CAUTION`. The type stands alone on the first line (`> [!TIP]`). The label goes in bold on the next line:
  - `**From Python:**` or `**From C#:**` inside a `> [!TIP]`;
  - `**Česky:**` inside a `> [!NOTE]`.
- **Code:** single `.java` files, or small folders of them, run with `java File.java`. There is no build tool until January. Target JDK 25; in S1 use compact `void main()` and `IO.println`.

## Blocks and tiers

A session page is a sequence of self-contained blocks, one `##` section each, with explanation, examples and exercises. Exercises come in three tiers: **Core** (everyone), **Extra** (more practice) and **Challenge** (strong students). Sessions are deliberately overpacked: one or two blocks more than will probably fit are marked *(if time allows)* and flagged `optional: true` in the frontmatter. Because blocks are self-contained, nothing may depend on a block that might move.

## Moving a block

After a lecture, for each block that was not covered:

1. Cut it from the session page and paste it at the top of the next session page.
2. Replace it on the original page with a one-line stub: `→ Continued in [S2](../02-name/README.md)`.
3. Mark it `moved-to-NN` in the old page's frontmatter, and `planned` in the new page's.

## Post-lecture update checklist

1. Push the code from `live/`.
2. Add a "Lecture notes" section with questions from class and points where students struggled.
3. Fix unclear examples.
4. Move unfinished blocks (see above).
5. Set `status: updated`.

## Big words

When a "big word" first appears on a session page, give an inline explanation of 1–3 sentences that agrees with the concept page's `summary`, then link to the concept page.

## Hard rules

- Never add solutions to any exercise. Solutions are not part of this repository.
- Never name the later project options. Students only know that the second half of the year is about building their own project.
- Never reference other repositories.
- Never commit or push. Committing is the course leader's job; leave changes in the working tree.
