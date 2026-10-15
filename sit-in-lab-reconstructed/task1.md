# [Original title not visible]

> **Reconstruction status:** This file follows the visible order and wording of the photographed `task1.md`. Text explicitly marked **Reconstructed** fills photographed gaps using the visible tests and source code; it is not represented as verbatim original wording. The original lab number is not visible.

> **Photograph coverage:** visible lines 15–100 and 118–164; lines 174–224 are only partly visible because the right pane is cut off. Lines 1–14, 101–117, and 165–173 were not photographed.

## Reminders

### Sample Run

You need to familiarise yourself with the sample run. The sample run shows the expected format. In some cases, we will not give the details of the format and you will need to deduce the format from the sample output.

## Using `List` and `ArrayList`

In this task, some methods accept a parameter of type `List`.

`List` is an **interface** in Java that represents an ordered collection of elements. It cannot be instantiated directly.

`ArrayList` is a class that **implements the `List` interface** and is commonly used when a `List` is required.

### Creating a List

```java
List<Object> values = new ArrayList<>();
```

### Common `List` Methods

| Method | Description | Example |
| --- | --- | --- |
| `add(E e)` | Append an element | `values.add(120);` |
| `get(int i)` | Get element at index | `values.get(0);` |
| `size()` | Number of elements | `values.size();` |

### Iterating Through a List

```java
for (Object x : values) {
  System.out.println(x);
}
```

### Example

```java
List<Object> values = new ArrayList<>();
values.add(120);
values.add(200.5);

System.out.println(values);
```

### Importing List

```java
import java.util.List;
```

## Description

This practical focuses on generics. In this version of the practical, there is **one task with two questions**. You will work with **one main class only** throughout the practical. In question 2, you will continue editing the same class from question 1 instead of creating a second top-level class.

Across question 1 and question 2, your solution must use wildcards, nested generics, `final` fields, a nested class, and a named package.

### Package Requirement

For this variant, place your class in the package `library`.
This means your source file must begin with the following declaration:

```java
package library;
```

It should exist in a subdirectory `library` and you should compile from the parent directory of the `library` folder.

### Compilation

Any code you created or edited that cannot be compiled will result in 0 marks for the given practical. Make sure your submission compiles by running the following command before submission.

```shell
javac library/Entry.java
```

The prose above is visible in the photograph. The command itself is below the photographed edge at line 100, but it is confirmed by the later terminal photographs.

## Question 1

> **Reconstructed bridge — original lines 101–117 were not photographed.** The class and field declarations below are recovered from `Test1`, the constructor table, and the edited source shown in later photographs.

Create a generic class `Entry<T>` in `library/Entry.java`. It stores an identifier in a `String` field named `isbnNumber` and a value in a field of type `T`. Both are private, final instance fields.

```java
package library;

public class Entry<T> {
  private final String isbnNumber;
  private final T value;
  // ...
}
```

| Method Name | `Entry` (constructor) |
| --- | --- |
| Parameters | `isbnNumber` of type `String`; `value` of type `T` |
| Description | Initializes an object with the given id and value. |
| Return | none |
| Usage | `new Entry<T>(isbnNumber, value)` |

| Method Name | `get` |
| --- | --- |
| Parameters | none |
| Description | Returns the stored value. |
| Return | `T` |
| Usage | `T item = entry.get()` |

| Method Name | `sameValueAs` |
| --- | --- |
| Parameters | `other` — an `Entry` whose value is compatible with the value stored in this object |
| Description | Returns `true` if the value stored in this object is equal to the value stored in `other`, according to `equals`. |
| Requirement | Use a wildcard in the parameter type. |
| Return | `boolean` |
| Usage | `entry.sameValueAs(other)` |

| Method Name | `storeIn` |
| --- | --- |
| Parameters | `list` — a list that can consume the value of this object |
| Description | Appends the stored value to the end of `list`. |
| Requirement | Use a wildcard in the parameter type. Make sure the type is the most flexible type. |
| Return | none |
| Usage | `entry.storeIn(list)` |

| Method Name | `pairWith` |
| --- | --- |
| Parameters | `other` of type `T` |
| Description | Returns a new `Entry` whose id is the same as this object and whose value is a `List<T>` containing this object's value followed by `other`. |
| Requirement | The return type must use **nested generics**. |
| Return | `Entry<List<T>>` |
| Usage | `Entry<List<T>> paired = entry.pairWith(other)` |

| Method Name | `toString` |
| --- | --- |
| Parameters | none |
| Description | **Reconstructed:** Returns the id, followed by `": "`, followed by the stored value. |
| Return | `String` |
| Usage | `entry.toString()` |

| Method Name | `equals` |
| --- | --- |
| Parameters | **Reconstructed:** `obj` of type `Object` |
| Description | **Reconstructed:** Returns `true` when `obj` is an `Entry` with the same id. Only the id field is compared; the stored values are not compared. |
| Return | `boolean` |
| Usage | `entry.equals(other)` |

The `toString` and `equals` rows fall in the unphotographed/partly clipped lines 165–175. Their behavior is confirmed by the visible `Test1` output; their wording above is reconstructed.

### Sample Run

> The heading and a sequence of `jshell>` prompts are visible at lines 177–209, but almost all text after each prompt is outside the right edge of the photograph. The following is a behaviorally equivalent reconstruction, not a verbatim transcription.

```text
jshell> Entry<Number> entry = new Entry<>("A", 120)
entry ==> A: 120

jshell> entry.get()
$.. ==> 120

jshell> Entry<Integer> other = new Entry<>("B", 120)
other ==> B: 120

jshell> entry.sameValueAs(other)
$.. ==> true

jshell> entry.sameValueAs(new Entry<Integer>("C", 200))
$.. ==> false

jshell> List<Object> values = new ArrayList<>()
values ==> []

jshell> entry.storeIn(values)

jshell> values
values ==> [120]

jshell> entry.pairWith(200)
$.. ==> A: [120, 200]
```

### Testing

Run the test with the following command:

```shell
java -cp test.jar:. Test1
```

The heading, opening words, and command prefix are visible in the photographed file; the complete command is confirmed by multiple terminal photographs.

### Submit

The photograph confirms a `### Submit` section beginning with “After completing …” followed by a shell command. The remainder of the sentence and the submission command are outside the photographed right edge. They are deliberately not invented here.

Question 2 is reconstructed separately in [question2.md](question2.md), because its original prose was not photographed.

