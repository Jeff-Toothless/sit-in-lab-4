# CS2030S Reconstructed Practical — Generic Library Entries

This practical focuses on generics. There is **one task with two questions**, and you will work with **one main class only** throughout the practical. In Question 2, continue editing the class from Question 1 instead of creating a second top-level class.

Across both questions, your solution must use wildcards, nested generics, `final` fields, a static nested class, and a named package.

> This is a completed reconstruction based on every supplied photograph. Photo-visible wording and reconstructed connective material have been combined into one coherent CS2030S-style exercise. The original lab number and submission command remain unavailable, so no lab number is claimed. See [EVIDENCE.md](EVIDENCE.md) for the exact evidence boundary.

## Reminders

### Sample Runs

Familiarise yourself with the sample runs. They show the expected format and behavior. Some details are intentionally left for you to deduce from the examples.

### Using `List` and `ArrayList`

`List` is an interface representing an ordered collection. It cannot be instantiated directly. `ArrayList` implements `List` and can be used when a `List` is required.

```java
import java.util.ArrayList;
import java.util.List;

List<Object> values = new ArrayList<>();
values.add(120);
values.add(200.5);

System.out.println(values); // [120, 200.5]
```

Useful operations include:

| Method | Description | Example |
| --- | --- | --- |
| `add(E e)` | Append an element | `values.add(120);` |
| `get(int i)` | Get the element at index `i` | `values.get(0);` |
| `size()` | Return the number of elements | `values.size();` |

You can iterate through a list as follows:

```java
for (Object x : values) {
  System.out.println(x);
}
```

## Package Requirement

Place your class in package `library`. Your source file must begin with:

```java
package library;
```

The file must be stored at:

```text
library/Entry.java
```

Compile it from the parent directory of `library`:

```shell
javac library/Entry.java
```

Code that does not compile receives no credit. Do not use raw types or unchecked casts.

## Question 1 — `Entry<T>`

Create a public generic class `Entry<T>`. It represents a library entry containing an ISBN identifier and a value of type `T`.

### Fields

Declare exactly these private, final instance fields:

```java
private final String isbnNumber;
private final T value;
```

### Constructor and Methods

| Member | Requirement |
| --- | --- |
| `Entry(String isbnNumber, T value)` | Initialize both fields with the supplied values. |
| `T get()` | Return the stored value. |
| `boolean sameValueAs(Entry<? extends T> other)` | Return whether this value equals the value in `other`. The wildcard must allow `Entry<Integer>` when the receiver is `Entry<Number>`. |
| `void storeIn(List<? super T> list)` | Append the stored value to the end of `list`. Use the most flexible consumer type. |
| `Entry<List<T>> pairWith(T other)` | Return a new entry with the same ISBN and a two-element list containing this value followed by `other`. The return type must use nested generics. |
| `String toString()` | Return `isbnNumber + ": " + value`. |
| `boolean equals(Object obj)` | Return `true` exactly when `obj` is an `Entry<?>` with the same ISBN. Do not compare stored values. Return `false` for `null` and non-`Entry` objects. |

You are not required to override `hashCode()` for this reconstructed practical because no photographed requirement or test mentions it.

### Question 1 Sample Run

```text
jshell> import library.Entry

jshell> import java.util.ArrayList

jshell> import java.util.List

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

jshell> entry.equals(new Entry<String>("A", "different value"))
$.. ==> true

jshell> entry.equals(new Entry<Integer>("B", 120))
$.. ==> false
```

## Question 2 — Static Nested `Holder<S>`

Continue editing `library.Entry<T>`. Inside it, declare a public generic static nested class named `Holder<S>`. Do not create another top-level class.

```java
public static class Holder<S> {
  // ...
}
```

Making it `static` is essential: a holder must be constructible without an enclosing `Entry` instance.

### Field, Constructor, and Methods

| Member | Requirement |
| --- | --- |
| `private final Entry<S> entry` | Store the held entry in a private, final instance field. |
| `Holder(Entry<S> entry)` | Initialize the field. This constructor has one explicit parameter. |
| `Entry<S> getEntry()` | Return the exact stored `Entry` object. |
| `boolean hasSameValue(Entry<? extends S> other)` | Return whether the held entry and `other` store equal values. It must accept `Entry<Integer>` when the holder stores `Entry<Number>`. |
| `void storeIn(List<? super S> list)` | Append the held entry's value to the end of `list`. |
| `String toString()` | Return the string representation of the held entry. |

### Question 2 Sample Run

```text
jshell> Entry<Number> entry = new Entry<>("A", 10)
entry ==> A: 10

jshell> Entry.Holder<Number> holder = new Entry.Holder<>(entry)
holder ==> A: 10

jshell> holder.getEntry() == entry
$.. ==> true

jshell> holder.hasSameValue(new Entry<Integer>("B", 10))
$.. ==> true

jshell> holder.hasSameValue(new Entry<Integer>("C", 20))
$.. ==> false

jshell> List<Object> values = new ArrayList<>()
values ==> []

jshell> holder.storeIn(values)

jshell> values
values ==> [10]
```

## Testing

Compile before testing:

```shell
javac library/Entry.java
```

Run each question separately:

```shell
java -cp test.jar:. Test1
java -cp test.jar:. Test2
```

Or run the complete reconstructed suite:

```shell
java -jar test.jar
```

The supplied replacement tests check declarations, generic signatures, variance, values, append order, nested-generic output, `equals` edge cases, static nesting, object identity, and all `Holder` behavior.

## Submission for This Reconstruction

The original submission command is outside the photographed area. For this reconstructed exercise, submit only:

```text
library/Entry.java
```

Do not submit the reference solution or compiled `.class` files.

## Project Layout

```text
.
├── README.md                 complete reconstructed exercise
├── task1.md                  photo-led Question 1 transcription
├── question2.md              evidence-led Question 2 notes
├── EVIDENCE.md               photograph map and uncertainty ledger
├── evidence/photos/          copies of all 11 supplied originals
├── library/Entry.java        intentionally incomplete starter
├── reference/library/Entry.java
│                              complete reference implementation
├── tests/src/                replacement test source
├── test.jar                  executable replacement tests
└── build-test-jar.sh         test-jar build script
```

## Reference Implementation

The starter file compiles but intentionally fails behavioral checks. To verify the reference implementation without overwriting the starter:

```shell
tmpdir="$(mktemp -d)"
mkdir -p "$tmpdir/library"
cp reference/library/Entry.java "$tmpdir/library/Entry.java"
cp test.jar "$tmpdir/test.jar"
cd "$tmpdir"
javac library/Entry.java
java -jar test.jar
```

Rebuild the replacement test archive with:

```shell
./build-test-jar.sh
```

The test harness targets Java 11 or newer and requires a JDK because it performs compile-time generic checks.

