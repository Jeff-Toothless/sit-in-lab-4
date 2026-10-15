# Evidence and reconstruction ledger

This file records exactly what the photographs establish and what remains reconstructed.

## Source set

The user supplied eleven photographs in the current conversation. The earlier referenced conversation contains three photographs that duplicate the final Question 1 and Question 2 debugging views. Copies of the eleven supplied originals are preserved under [`evidence/photos/`](evidence/photos/).

The official course homepage identifies the active offering as [CS2030S AY 2026/27 Semester 1](https://nus-cs2030s.github.io/2627-s1/). The computer clock in the photographs reads `Oct 8`, establishing 8 October 2026. Neither the photographed title nor any visible text establishes the lab-exercise number.

## Photograph map

| Local photograph | Visible evidence |
| --- | --- |
| `01-task-lines-48-100.jpg` | `task1.md` lines 48–100: list iteration, example, import, description, package requirement, compilation prose |
| `02-task-lines-15-70-and-code.jpg` | `task1.md` lines 15–70: reminders and `List`/`ArrayList` primer; early `Entry` attempt and compiler errors |
| `03-task-lines-118-164.jpg` | constructor, `get`, `sameValueAs`, `storeIn`, `pairWith`, and start of `toString` method tables |
| `04`–`06` | early `Entry.java` attempts using `id`, plus compiler errors |
| `07-task-lines-174-224-clipped.jpg` | ends of method table, sample-run prompts, Testing and Submit headings; most line contents are cut off by the photograph edge |
| `08`–`10` | complete visible `Test1` expectations and later `Entry.java` attempts |
| `11-test2-and-holder.jpg` | `Test2` expectations and a non-static `Holder<S>` attempt |

## Directly transcribed task text

The following portions of [task1.md](task1.md) are substantially visible and transcribed in their original order:

- `## Reminders` and `### Sample Run` guidance;
- `## Using List and ArrayList`, including creation, common methods, iteration, example, and import;
- `## Description`;
- `### Package Requirement`;
- `### Compilation` prose;
- the method tables for the constructor, `get`, `sameValueAs`, `storeIn`, and `pairWith`;
- the `toString` heading and its `Parameters: none` row;
- the `### Sample Run`, `### Testing`, and `### Submit` headings near the end.

## Missing photographed regions

- lines 1–14, including the original title and any lab number;
- lines 101–117, which likely introduce Question 1 and its fields;
- lines 165–173, covering most of `toString` and `equals` documentation;
- most contents of sample-run lines 177–209, because the right pane is outside the photograph;
- the end of the Testing sentence and most of its command, although terminal photographs confirm `java -cp test.jar:. Test1`;
- the Submit sentence and submission command;
- the complete Question 2 statement.

These regions are labeled **Reconstructed** wherever replacement material is supplied.

## Photo-confirmed Test1 expectations

```text
Entry class should exist..
library.Entry is a generic class..
library.Entry is correctly declared..
library.Entry correctly declares the field isbnNumber..
library.Entry correctly declares the field value..
library.Entry correctly declares its constructor..
library.Entry correctly declares the method get..
library.Entry correctly declares the method sameValueAs..
library.Entry correctly declares the method storeIn..
library.Entry correctly declares the method pairWith..
library.Entry correctly declares the method toString..
library.Entry correctly declares the method equals..
sameValueAs() should accept Entry<Integer> when receiver is Entry<Number>..
storeIn() should accept ArrayList<Object> when receiver is Entry<Number>..
pairWith() should return nested generic Entry<List<T>>..
get() on first Entry returns 120..
toString() on first Entry returns A: 120..
sameValueAs() should allow subtype value returns true..
sameValueAs() should reject different value returns false..
storeIn() should append to destination list returns [120]..
pairWith() should produce a nested generic value returns A: [120, 200]..
equals() should compare only the id field returns true..
equals() should reject a different id returns false..
```

The terminal proves that `protected final String isbnNumber` is rejected. The exact field sentence is in missing lines 101–117; `private final String isbnNumber` is a high-confidence reconstruction based on the companion `private final T value`, information hiding, and the declaration test.

## Photo-confirmed Test2 expectations

```text
Entry class should exist..
nested Holder class should exist..
library.Entry$Holder correctly declares the field entry..
library.Entry$Holder correctly declares its constructor..
library.Entry$Holder correctly declares the method getEntry..
library.Entry$Holder correctly declares the method hasSameValue..
library.Entry$Holder correctly declares the method storeIn..
library.Entry$Holder correctly declares the method toString..
Holder should be a static nested class that can be created without an outer instance..
hasSameValue() should accept Entry<Integer> when holder stores Entry<Number>..
getEntry() should return the stored entry returns A: 10..
hasSameValue() should allow subtype values returns true..
```

The photograph ends below this point. Replacement checks for `Holder.storeIn()` and `Holder.toString()` follow directly from their confirmed declarations but their original behavioral-test wording is unknown.

## Deliberate additions in the completed reconstruction

At the user's request, the replacement tests now extend the photo-visible checks in ways that follow directly from the completed README requirements:

- `storeIn` is checked with a non-empty destination to verify that it appends at the end;
- `pairWith` is checked to return a new `Entry` with values in the required order;
- `equals` is checked with the same object, `null`, and a non-`Entry` object;
- `Holder.getEntry` is checked by object identity, not only by `equals`;
- `Holder.hasSameValue` is checked with both equal and different values;
- `Holder.storeIn` and `Holder.toString` receive full behavioral checks.

These are reconstruction tests, not claims about unseen original hidden tests.

## Constructor inference for `Holder<S>`

The screen reports:

```text
Available constructors:
- public Holder(Entry, Entry)
```

The source beside it shows one explicit parameter and a non-static member class. Java adds an implicit enclosing `Entry` parameter to such a constructor. Therefore the correct static nested class has the single explicit constructor `Holder(Entry<S> entry)`; two source parameters would contradict the photographed code and static-class requirement.

## Still unavailable and deliberately not invented

- original title and lab number;
- exact text in the missing line ranges listed above;
- original Question 2 prose and sample run;
- time limit and mark allocation, if present in lines 1–14;
- exact submission platform and command;
- original starter-code omissions;
- hidden tests and the original `test.jar` binary.

