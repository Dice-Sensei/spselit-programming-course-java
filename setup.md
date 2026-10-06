---
type: guide
title: Setup
status: draft
---

# Setup

You need **JDK 25 or newer** (the Java Development Kit). The course is written for 25; any newer version works too. Nothing else is required to start. IntelliJ IDEA is optional and can come later.

## 1. Install the JDK 25

Any JDK 25 distribution works. [Eclipse Temurin](https://adoptium.net/) is a good, free choice. If you are unsure which one to pick, see [Java versions and who provides them](#java-versions-and-who-provides-them).

### Windows

Download the Windows installer (`.msi`) for JDK 25 and run it. In the installer, enable the options that add Java to `PATH` and set `JAVA_HOME`.

### macOS

Download the `.pkg` installer for JDK 25 and run it. Choose the right one for your Mac: **Apple Silicon** (M1, M2, M3 and newer, `aarch64`) or **Intel** (`x64`). If unsure, open the Apple menu → About This Mac: "Chip: Apple M…" means Apple Silicon.

### Linux

Install JDK 25 from your package manager, for example `sudo apt install openjdk-25-jdk` on Debian or Ubuntu if the package is available, or unpack the `.tar.gz` from Temurin and add its `bin` folder to `PATH`.

> [!TIP]
> If the Wi-Fi is slow, don't download in class. The leader has the installers on a USB stick that goes around.

## 2. Check your Java version

Open a terminal (on Windows: PowerShell or Command Prompt) and run:

```
java -version
```

The first line of the output must show version **25 or higher**, for example `openjdk version "25" ...`. If it shows a lower number (such as 17 or 21) or nothing at all, see [Common problems](#common-problems).

## 3. Your first program without an IDE

1. Open any text editor and type:

   ```java
   void main() {
       IO.println("Hello, Java!");
   }
   ```

2. Save it as `Hello.java`.
3. In the terminal, go to the folder with the file and run:

   ```
   java Hello.java
   ```

You should see `Hello, Java!`.

## 4. Install IntelliJ IDEA

Download the [**IntelliJ IDEA**](https://www.jetbrains.com/idea/) and install it. It is free (the base version).

> [!TIP]
> **Education edition available:**
> Students with ISIC can apply for whole JetBrains suite - access tools at no cost for the duration of your studies.

## 5. On a weaker laptop

If your laptop has less than about 8 GB of RAM, IntelliJ may be slow. Keep using a text editor and the terminal, or use VS Code with the "Extension Pack for Java". Nothing in the autumn needs an IDE.

## 6. Getting the course files

*From S2/S3 on.* In S1 you only read the wiki on GitHub; there is nothing to clone yet.

```
git clone https://github.com/Dice-Sensei/spselit-programming-course-java.git
```

Or in IntelliJ: File → New → Project from Version Control, and paste the URL `https://github.com/Dice-Sensei/spselit-programming-course-java.git`.

## Java versions and who provides them

### Versions

- A new Java version is released **every six months**, in March and September. The number goes up by one each time (24, 25, 26, 27, ...).
- Every two years one version is an **LTS** (Long-Term Support) release, which gets updates for many years. The LTS versions are 8, 11, 17, 21 and **25**. Versions in between (26, 27, ...) are supported only until the next release comes out.
- This course uses **25**, the current LTS, because it is the first LTS where the compact `void main()` and `IO.println` used in S1 are final features. A newer version (26, 27, ...) also works.
- Older versions (8, 11, 17, 21) are still used in industry and may already be on your laptop. Programs written for them run on 25, but our S1 examples do not run on them.

> [!NOTE]
> **Česky:**
> Nová verze Javy vychází každého půl roku (březen, září). LTS verze (8, 11, 17, 21, 25) mají dlouhou podporu. V kurzu používáme 25 nebo novější.

### Who provides the JDK

Java itself is open source (the **OpenJDK** project). Several companies build the OpenJDK source code and publish a ready-to-install JDK, which they call a *distribution* or *build*. They all contain the same Java; they differ in who maintains them, how long they provide updates, and license terms.

| Provider | Distribution | Notes |
|---|---|---|
| Eclipse Foundation (Adoptium) | Temurin | Free, community-driven, a safe default for learning |
| Oracle | Oracle JDK, and OpenJDK builds at jdk.java.net | Oracle JDK has its own license; check it before using it commercially. The jdk.java.net builds are for the latest version only and get no long-term updates |
| Amazon | Corretto | Free, used in Amazon's own services |
| Microsoft | Microsoft Build of OpenJDK | Free |
| Azul | Zulu | Free builds, paid support available |
| BellSoft | Liberica | Free builds, paid support available |
| Red Hat, SAP, and others | Their own builds | Mostly used with their own products |

For this course, **any of these works**, as long as it is version 25 or newer. Your package manager or IntelliJ may also offer to download a JDK for you (IntelliJ: File → Project Structure → SDKs → Add SDK → Download JDK).

> [!TIP]
> **From Python:**
> This is like having CPython, Anaconda and PyPy: one language, several builds from different providers. In Java the choice of vendor rarely matters for your code, but the **version number** does.

## Common problems

### `java -version` shows a version below 25, or `void main()` gives a "preview" error

You probably have an older JDK left over from another class (for example Java 21). On an older JDK, the compact `void main()` program fails with a confusing error mentioning *preview features* or an *unnamed class*. Check that the first line of `java -version` shows 25 or higher. If not, install JDK 25 or newer (step 1) and open a **new** terminal. If several JDKs are installed, make sure `PATH` and `JAVA_HOME` point to the new JDK (see [Setting PATH and JAVA_HOME](#setting-path-and-java_home)).

### Notepad saved `Hello.java.txt`

On Windows, Notepad may save the file as `Hello.java.txt`, and then `java Hello.java` says the file is not found. In File Explorer, open View → Show → File name extensions, then rename the file to `Hello.java`. In Notepad's Save dialog, set "Save as type" to "All files".

### `java` is not recognized as a command (Windows)

Close the terminal and open a new one after installing; an already open terminal doesn't see the new `PATH`. If it still fails, check that the JDK's `bin` folder is in `PATH`, or rerun the installer with the "Add to PATH" option enabled. Or set it by hand, as described next.

### Setting PATH and JAVA_HOME

A standard Java setup has `JAVA_HOME` pointing to the JDK folder and the JDK's `bin` folder on `PATH`. Use this if the installer didn't do it. Replace the folder with where your JDK actually is.

**Windows:** press the Windows key, search for "Edit the system environment variables", click "Environment Variables". Under "User variables" add a new variable `JAVA_HOME` with the JDK folder (for example `C:\Program Files\Eclipse Adoptium\jdk-25`). Then select `Path`, click Edit → New, and add `%JAVA_HOME%\bin`. Click OK everywhere and open a **new** terminal.

**macOS / Linux:** add these lines to `~/.zshrc` (macOS) or `~/.bashrc` (Linux), then open a new terminal. On macOS the JDK path can be found with `/usr/libexec/java_home -v 25`.

```
export JAVA_HOME=/path/to/jdk-25
export PATH="$JAVA_HOME/bin:$PATH"
```

Check with `java -version` and `echo $JAVA_HOME` (Windows PowerShell: `echo $env:JAVA_HOME`).
