package library;

import java.util.ArrayList;
import java.util.List;

/** Reference implementation for the reconstructed exercise. */
public class Entry<T> {
  private final String isbnNumber;
  private final T value;

  public Entry(String isbnNumber, T value) {
    this.isbnNumber = isbnNumber;
    this.value = value;
  }

  public T get() {
    return this.value;
  }

  public boolean sameValueAs(Entry<? extends T> other) {
    return this.value.equals(other.value);
  }

  public void storeIn(List<? super T> list) {
    list.add(this.value);
  }

  public Entry<List<T>> pairWith(T other) {
    List<T> list = new ArrayList<>();
    list.add(this.value);
    list.add(other);
    return new Entry<>(this.isbnNumber, list);
  }

  @Override
  public String toString() {
    return this.isbnNumber + ": " + this.value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Entry<?>) {
      Entry<?> other = (Entry<?>) obj;
      return this.isbnNumber.equals(other.isbnNumber);
    }
    return false;
  }

  public static class Holder<S> {
    private final Entry<S> entry;

    public Holder(Entry<S> entry) {
      this.entry = entry;
    }

    public Entry<S> getEntry() {
      return this.entry;
    }

    public boolean hasSameValue(Entry<? extends S> other) {
      return this.entry.sameValueAs(other);
    }

    public void storeIn(List<? super S> list) {
      this.entry.storeIn(list);
    }

    @Override
    public String toString() {
      return this.entry.toString();
    }
  }
}

