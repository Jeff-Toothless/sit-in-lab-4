# Question 2 — Static Nested `Holder<S>`

> **Test-backed reconstruction.** None of the supplied photographs shows the Question 2 statement itself. Every declaration below is confirmed by the visible `Test2` output and edited source. Explanatory prose and examples are reconstructed rather than verbatim original text.

Continue editing the same `library.Entry<T>` class from Question 1. Do not create another top-level class.

Inside `Entry<T>`, declare the public generic static nested class `Holder<S>`:

```java
public static class Holder<S> {
  // ...
}
```

`Holder<S>` stores one `Entry<S>` in a private final field named `entry`.

| Member | Required declaration and behavior |
| --- | --- |
| field | `private final Entry<S> entry` |
| constructor | `public Holder(Entry<S> entry)` initializes the field |
| `getEntry` | `public Entry<S> getEntry()` returns the stored entry |
| `hasSameValue` | `public boolean hasSameValue(Entry<? extends S> other)` compares the held entry's value with `other` |
| `storeIn` | `public void storeIn(List<? super S> list)` stores the held value in `list` |
| `toString` | returns the string representation of the stored entry |

`Holder` must be static so that it can be created without an enclosing `Entry` instance:

```java
Entry.Holder<Number> holder =
    new Entry.Holder<>(new Entry<Number>("A", 10));
```

`hasSameValue` must accept an `Entry<Integer>` when the holder stores an `Entry<Number>`:

```java
boolean same = holder.hasSameValue(new Entry<Integer>("B", 10));
```

The visible tests confirm that:

- `getEntry()` returns the stored entry, printed as `A: 10`;
- `hasSameValue()` allows subtype values and returns `true` for the example above;
- `Holder<S>` is a static nested class that can be instantiated without an outer `Entry` object.

Compile and run the Question 2 test from the parent directory of `library/`:

```shell
javac library/Entry.java
java -cp test.jar:. Test2
```

## Why the photograph reports `Holder(Entry, Entry)`

The photographed attempt declared `public class Holder<S>` without `static`. Java therefore inserted a hidden enclosing `Entry` parameter. Reflection displayed:

```text
Available constructors:
- public Holder(Entry, Entry)
```

Only one parameter was written in the source. After the class is correctly declared `static`, the required source-level constructor is still the single-parameter:

```java
public Holder(Entry<S> entry)
```

