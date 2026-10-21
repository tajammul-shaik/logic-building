# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repository is

A personal Java practice/learning repo (`logic-building`). Each file is a self-contained exercise — a data structure trick, an OOP concept, a design pattern, or a streams example — demonstrated via its own `main` method with `System.out.println` output rather than assertions. There is no test framework (no JUnit) and no application entry point tying files together.

## Commands

Build (compiles everything under `src/main/java`):
```
mvn compile
```

Run a single exercise (after compiling), e.g. the singleton demo:
```
mvn exec:java -Dexec.mainClass="exercise.design.patterns.creational.singleton.SingletonTest"
```
Note: the `exec-maven-plugin` is not declared in `pom.xml`, so either add it or run compiled classes directly via `java -cp target/classes <FQCN>` (see below).

Run directly with `javac`/`java` (no Maven plugin needed):
```
javac -d target/classes $(Get-ChildItem -Recurse -Path src/main/java -Filter *.java | Select-Object -ExpandProperty FullName)
java -cp target/classes exercise.oop.DayOneMastery
```
`HelloWorld.java` lives directly under `src/main/java` with no package, so run it as `java -cp target/classes HelloWorld`.

There is no linter, formatter, or test suite configured — `mvn compile` (or a successful `javac`) is the only correctness signal.

## Architecture / structure

- `pom.xml` targets Java 21, has no external dependencies — everything uses the JDK standard library only.
- All source lives under `src/main/java/exercise/...`, grouped by topic, not by a shared application architecture:
  - `exercise/oop` — core OOP concepts: `DayOneMastery`, `OOPConceptBankExample`, `TestDataTypes` (variables/scope, data types), `inheritance/Inheritance`, plus `abstractAndInterfaceExample/fleet_logistic`, a small multi-class scenario (`Vehicle`, `DeliveryTruck`, `DeliveryDrone`, `GPSDependable`, `AutonomousCapable`, `SmartPackageTracker`) showing abstract classes vs. interfaces and polymorphism, driven by `MainClass`.
  - `exercise/design/patterns/creational/singleton` — non-thread-safe vs. thread-safe singleton, exercised concurrently from `SingletonTest`.
  - `exercise/streams` — Java Stream API examples (`StreamExercise`, `StreamsExamples`).
  - `exercise/practice` — standalone algorithm/logic problems (e.g. `FindFirstRepeatingCharacter`).
  - `exercise/collections` — `java.util` Collection/Comparable/Comparator best practices and pitfalls, each demonstrated by actually triggering the failure at runtime (not just described in a comment): `CollectionBestPractices` (list/set/map gotchas — `ConcurrentModificationException`, ordering guarantees, immutability, null handling, thread safety); `comparable/` (`Employee` + `ComparableDemo`, showing a `compareTo` inconsistent with `equals` silently dropping an element from a `TreeSet`); `comparator/` (`Product` + `ProductComparators` + `ComparatorDemo`, showing `Comparator.comparing`/`thenComparing`/`reversed`/`nullsFirst` plus a truncating comparator that throws `IllegalArgumentException: Comparison method violates its general contract!`); `framework/` — a guided tour of the whole Collections Framework: `CollectionFrameworkGuide` (interface hierarchy + a "what to reach for" decision table), `ListImplementationsDemo`, `SetImplementationsDemo`, `QueueAndDequeImplementationsDemo`, `MapImplementationsDemo` (each documents how its implementations are laid out in memory — array vs. linked nodes vs. red-black tree vs. bit vector vs. hash bucket table — and proves the behavioral consequences at runtime, e.g. `ArrayList` vs `LinkedList` random-access timing, an LRU cache built from `LinkedHashMap`, `ConcurrentHashMap` correctness under real concurrent writers). Note: reflecting into JDK-internal fields (e.g. `ArrayList.elementData`) to literally observe array growth is blocked by module encapsulation on Java 21 without `--add-opens`, so these demos prove behavior functionally (timing, ordering, eviction, concurrency) rather than via reflection.
- Each topic folder is independent; there are no cross-package dependencies between exercises. When adding a new exercise, follow the existing pattern: put it in the topic-matching package and give it its own `main` method (or a `*Test`/`Main*` runner class if the exercise spans multiple collaborating classes, as `fleet_logistic` and `singleton` do).
- `target/` is a stale Maven build output directory (gitignored) — it currently contains compiled classes for source files that no longer exist in `src/` (e.g. `exercise.oop.BankAccount`, `exercise.oop.inheritance.Animal`/`Dog`/`Labrador`). Don't treat `target/classes` as a source of truth for what currently exists; always check `src/main/java`.